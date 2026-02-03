package hu.alphabox.jgc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(IllegalStateException.class)
  ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
    return handleExceptionInternal(ex, null, HttpHeaders.EMPTY, HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<Object> handleUnknownException(Exception exception, WebRequest request) {
    return handleExceptionInternal(exception, null, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
    log.error("Exception occurred: {}", ex.getMessage(), ex);
    return super.handleExceptionInternal(ex, body, headers, statusCode, request);
  }
}
