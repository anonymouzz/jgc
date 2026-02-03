package hu.alphabox.jgc.github.webhook;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
interface WebhookClient {

  String EVENT_NAME_HEADER = "x-github-event";

  @PostExchange(
      contentType = MediaType.APPLICATION_JSON_VALUE,
      accept = MediaType.ALL_VALUE
  )
  void sendEvent(
      @RequestBody @Valid GitHubWebhookEvent webhookEvent,
      @RequestHeader(EVENT_NAME_HEADER) String event
  );
}
