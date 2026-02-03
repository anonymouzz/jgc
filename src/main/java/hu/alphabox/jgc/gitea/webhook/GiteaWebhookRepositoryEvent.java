package hu.alphabox.jgc.gitea.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.EnumNamingStrategies;
import tools.jackson.databind.annotation.EnumNaming;

public record GiteaWebhookRepositoryEvent(
    @NotNull
    Action action,
    @NotNull
    @Valid
    GiteaWebhookRepository repository
) {

  @EnumNaming(EnumNamingStrategies.LowerCaseStrategy.class)
  public enum Action {
    CREATED,
    DELETED
  }


}
