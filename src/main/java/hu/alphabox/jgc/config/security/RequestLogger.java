package hu.alphabox.jgc.config.security;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StreamUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class RequestLogger {

  public static void log(HttpServletRequest request, Logger log) {
    String headers = Collections.list(request.getHeaderNames())
        .stream()
        .map(name -> {
          if (HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name)) {
            return "Authorization: REDACTED";
          }
          return "%s: %s".formatted(name, Collections.list(request.getHeaders(name)));
        })
        .collect(Collectors.joining("\n"));

    log.warn(
        "{} {}\nParams: {}\n{}",
        request.getMethod(),
        request.getRequestURI(),
        request.getQueryString(),
        headers
    );
    if (log.isDebugEnabled()) {
      try {
        log.debug("Body: {}", StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8));
      } catch (IOException e) {
        log.debug("Cannot fetch request body with message: {}", e.getMessage());
      }
    }
  }

}
