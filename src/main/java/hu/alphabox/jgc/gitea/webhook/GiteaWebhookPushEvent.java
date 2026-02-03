package hu.alphabox.jgc.gitea.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GiteaWebhookPushEvent(
    List<@NotNull @Valid Commit> commits,
    @NotNull
    @Valid
    GiteaWebhookRepository repository
) {

  public record Commit(
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

    public record Author(
        @NotBlank
        String email
    ) {

    }

  }

}
