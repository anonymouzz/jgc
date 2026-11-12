package hu.alphabox.jgc.gitea.client;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.unit.DataSize;

/**
 * Caches successful GET responses by full request URI for a short TTL.
 * Concurrent requests for the same URI are collapsed into one Gitea call.
 * Non-2xx responses and non-GET requests pass through untouched.
 *
 * <p>No explicit invalidation: list endpoints (pull request pages, refs,
 * commit history) can be up to {@code ttl} stale. Acceptable for the
 * backfill, where Jira takes a snapshot anyway and later changes arrive
 * through webhooks.
 */
@Slf4j
class GiteaResponseCacheInterceptor implements ClientHttpRequestInterceptor {

  static final String CACHE_NAME = "gitea_response";

  private final Cache<String, BufferedClientHttpResponse> cache;

  GiteaResponseCacheInterceptor(Duration ttl, DataSize maxSize, MeterRegistry meterRegistry) {
    Cache<String, BufferedClientHttpResponse> built = Caffeine.newBuilder()
        .expireAfterWrite(ttl)
        .maximumWeight(maxSize.toBytes())
        .weigher((String key, BufferedClientHttpResponse value) -> key.length() + value.bodyLength() + 256)
        .recordStats()
        .build();
    this.cache = CaffeineCacheMetrics.monitor(meterRegistry, built, CACHE_NAME);
    log.info("Gitea response cache enabled: ttl={}, maxSize={}", ttl, maxSize);
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    if (request.getMethod() != HttpMethod.GET) {
      return execution.execute(request, body);
    }

    String key = request.getURI().toString();
    try {
      return cache.get(key, _ -> load(request, body, execution));
    } catch (Passthrough e) {
      return e.response;
    } catch (UncheckedIOException e) {
      throw e.getCause();
    }
  }

  private BufferedClientHttpResponse load(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
    try (ClientHttpResponse response = execution.execute(request, body)) {
      BufferedClientHttpResponse buffered = BufferedClientHttpResponse.of(response);
      if (!buffered.getStatusCode().is2xxSuccessful()) {
        throw new Passthrough(buffered);
      }
      return buffered;
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Carries a response that must reach the caller but must not be cached.
   * Thrown from the Caffeine loader so no mapping is recorded.
   */
  private static final class Passthrough extends RuntimeException {

    private final transient ClientHttpResponse response;

    private Passthrough(ClientHttpResponse response) {
      super(null, null, false, false);
      this.response = response;
    }
  }
}
