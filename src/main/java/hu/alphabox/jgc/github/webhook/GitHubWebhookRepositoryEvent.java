package hu.alphabox.jgc.github.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.EnumNamingStrategies;
import tools.jackson.databind.annotation.EnumNaming;

record GitHubWebhookRepositoryEvent(
    @NotNull
    Action action,
    @NotNull
    @Valid
    GitHubWebhookRepository repository,
    @NotNull
    @Valid
    GitHubWebhookInstallation installation
) implements GitHubWebhookEvent {

  @EnumNaming(EnumNamingStrategies.LowerCaseStrategy.class)
  enum Action {
    CREATED,
    DELETED
  }
}
