package hu.alphabox.jgc.gitea.client;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * Global cap on in-flight Gitea calls. One instance is shared by every Gitea
 * client so the limit stays global. Sits after the response cache, so cache
 * hits do not take a permit. The body is read inside the permit, so Gitea is
 * done with the request before the permit is released.
 */
class GiteaConcurrencyLimitInterceptor implements ClientHttpRequestInterceptor {

  private final Semaphore permits;

  GiteaConcurrencyLimitInterceptor(int limit) {
    this.permits = new Semaphore(limit);
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    permits.acquireUninterruptibly();
    try (ClientHttpResponse response = execution.execute(request, body)) {
      return BufferedClientHttpResponse.of(response);
    } finally {
      permits.release();
    }
  }
}
