package hu.alphabox.jgc.github.webhook;

class WebhookException extends RuntimeException {

  WebhookException(String message) {
    super(message);
  }

  static class WebhookClientException extends WebhookException {

    WebhookClientException(String responseBody) {
      super("Webhook callee related exception occurred. (error response body: %s)".formatted(responseBody));
    }
  }

  static class WebhookServerException extends WebhookException {

    WebhookServerException(String responseBody) {
      super("Webhook server related exception occurred. (error response body: %s)".formatted(responseBody));
    }
  }
}
