package hu.alphabox.jgc.gitea.client;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import java.io.IOException;
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
 * Non-2xx responses and non-GET requests pass through untouched.
 *
 * <p>The Gitea call runs outside the cache: a loading {@code get} would hold
 * a hash-bin lock for the whole call, and one hung call then blocks other
 * keys and expiry. Concurrent misses for the same URI each call Gitea.
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
    BufferedClientHttpResponse cached = cache.getIfPresent(key);
    if (cached != null) {
      return cached;
    }

    BufferedClientHttpResponse buffered;
    try (ClientHttpResponse response = execution.execute(request, body)) {
      buffered = BufferedClientHttpResponse.of(response);
    }
    if (buffered.getStatusCode().is2xxSuccessful()) {
      cache.put(key, buffered);
    }
    return buffered;
  }
}
