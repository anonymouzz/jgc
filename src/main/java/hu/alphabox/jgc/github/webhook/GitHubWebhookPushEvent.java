package hu.alphabox.jgc.github.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record GitHubWebhookPushEvent(
    List<@NotNull @Valid Commit> commits,
    @NotNull
    @Valid
    GitHubWebhookRepository repository,
    @NotNull
    @Valid
    GitHubWebhookInstallation installation
) implements GitHubWebhookEvent {

  record Commit(
      @NotBlank
      String id,
      @NotBlank
      String message,
      @NotBlank
      String url,
      @NotNull
      @Valid
      Author author,
      @NotNull
      ZonedDateTime timestamp,
      @NotNull
      List<@NotBlank String> added,
      @NotNull
      List<@NotBlank String> removed,
      @NotNull
      List<@NotBlank String> modified
  ) {

    record Author(
        @NotBlank
        String email
    ) {

    }

  }

}
