package hu.alphabox.jgc.github.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record GitHubWebhookIssueCommentEvent(
    String action,
    Issue issue,
    Comment comment,
    GitHubWebhookRepository repository,
    @NotNull
    @Valid
    GitHubWebhookInstallation installation
) implements GitHubWebhookEvent {

  record Issue(
      Long number
  ) {

  }

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record Comment(
      Long id,
      String body,
      GitHubWebhookUser user,
      String htmlUrl
  ) {

  }
}
