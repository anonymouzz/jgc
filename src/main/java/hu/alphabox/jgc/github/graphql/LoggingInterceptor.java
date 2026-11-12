package hu.alphabox.jgc.github.graphql;

import graphql.execution.ExecutionId;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.ResponseError;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
class LoggingInterceptor implements WebGraphQlInterceptor {

  private static final int MAX_VARIABLES_LENGTH = 1000;
  private static final int MAX_ERRORS_LENGTH = 1500;

  private final boolean logRequests;

  /**
   * @param logRequests log every GraphQL request and the shape of its response.
   *                    Env: APPLICATION_GRAPHQL_LOGREQUESTS. Default: false.
   */
  LoggingInterceptor(@Value("${application.graphql.log-requests:false}") boolean logRequests) {
    this.logRequests = logRequests;
    if (logRequests) {
      log.info("GraphQL request logging enabled");
    }
  }

  @Override
  public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
    log.debug(request.toString());

    long startNanos = System.nanoTime();
    if (logRequests) {
      logRequest(request);
    }

    return chain.next(request).doOnNext(response -> {
      if (logRequests) {
        logResponse(request, response, startNanos);
      }
      if (response.isValid()) {
        return;
      }

      ExecutionId executionId = response.getExecutionInput().getExecutionId();
      String validationErrors = response.getErrors().stream()
          .map(error -> "- type=%s message=%s path=%s".formatted(
              error.getErrorType(),
              error.getMessage(),
              error.getPath()
          ))
          .collect(Collectors.joining(System.lineSeparator()));

      log.warn("Validation error occurred for {}:{}{}", executionId, System.lineSeparator(), validationErrors);
    });
  }

  /**
   * Logs the document and the variables only. Headers are not logged,
   * because they carry the Authorization token.
   */
  private void logRequest(WebGraphQlRequest request) {
    try {
      log.info(
          "GraphQL request: id={} operation={} variables={} document={}",
          request.getId(),
          request.getOperationName(),
          GraphQlLogFormatter.cut(String.valueOf(request.getVariables()), MAX_VARIABLES_LENGTH),
          GraphQlLogFormatter.document(request.getDocument())
      );
    } catch (RuntimeException e) {
      log.warn("Could not log GraphQL request", e);
    }
  }

  private void logResponse(WebGraphQlRequest request, WebGraphQlResponse response, long startNanos) {
    try {
      List<ResponseError> errors = response.getErrors();
      String errorText = errors.stream()
          .map(error -> "type=%s message=%s path=%s".formatted(
              error.getErrorType(),
              error.getMessage(),
              error.getPath()
          ))
          .collect(Collectors.joining("; "));
      Object data = response.getData();

      log.info(
          "GraphQL response: id={} valid={} elapsedMs={} errorCount={} errors=[{}] shape={}",
          request.getId(),
          response.isValid(),
          (System.nanoTime() - startNanos) / 1_000_000,
          errors.size(),
          GraphQlLogFormatter.cut(errorText, MAX_ERRORS_LENGTH),
          GraphQlLogFormatter.shape(data)
      );
    } catch (RuntimeException e) {
      log.warn("Could not log GraphQL response", e);
    }
  }
}
