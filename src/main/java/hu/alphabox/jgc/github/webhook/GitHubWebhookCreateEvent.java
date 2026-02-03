package hu.alphabox.jgc.github.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
record GitHubWebhookCreateEvent(
    @NotBlank
    String ref,
    @NotNull
    GitHubWebhookRefType refType,
    @NotNull
    @Valid
    GitHubWebhookRepository repository,
    @NotNull
    @Valid
    GitHubWebhookInstallation installation
) implements GitHubWebhookEvent {

}
