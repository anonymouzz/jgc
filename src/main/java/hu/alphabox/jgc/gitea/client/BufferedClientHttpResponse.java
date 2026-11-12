package hu.alphabox.jgc.gitea.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

/**
 * Fully read response. Safe to share between callers: every {@link #getBody()}
 * call returns a fresh stream over the same bytes and {@link #close()} is a no-op.
 */
final class BufferedClientHttpResponse implements ClientHttpResponse {

  private final HttpStatusCode statusCode;
  private final String statusText;
  private final HttpHeaders headers;
  private final byte[] body;

  private BufferedClientHttpResponse(HttpStatusCode statusCode, String statusText, HttpHeaders headers, byte[] body) {
    this.statusCode = statusCode;
    this.statusText = statusText;
    this.headers = headers;
    this.body = body;
  }

  static BufferedClientHttpResponse of(ClientHttpResponse response) throws IOException {
    HttpHeaders headers = new HttpHeaders();
    response.getHeaders().forEach(headers::put);

    byte[] body;
    try (InputStream in = response.getBody()) {
      body = in.readAllBytes();
    }

    return new BufferedClientHttpResponse(
        response.getStatusCode(),
        response.getStatusText(),
        HttpHeaders.readOnlyHttpHeaders(headers),
        body
    );
  }

  int bodyLength() {
    return body.length;
  }

  @Override
  public HttpStatusCode getStatusCode() {
    return statusCode;
  }

  @Override
  public String getStatusText() {
    return statusText;
  }

  @Override
  public HttpHeaders getHeaders() {
    return headers;
  }

  @Override
  public InputStream getBody() {
    return new ByteArrayInputStream(body);
  }

  @Override
  public void close() {
    // nothing to release
  }
}
