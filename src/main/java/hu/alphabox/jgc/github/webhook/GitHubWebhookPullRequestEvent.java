package hu.alphabox.jgc.github.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;
import tools.jackson.databind.EnumNamingStrategies.LowerCaseStrategy;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.EnumNaming;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record GitHubWebhookPullRequestEvent(
    String action,
    @Positive
    Integer number,
    @NotNull
    @Valid
    PullRequest pullRequest,
    @NotNull
    @Valid
    GitHubWebhookRepository repository,
    @Valid
    Changes changes,
    @NotNull
    @Valid
    GitHubWebhookInstallation installation
) implements GitHubWebhookEvent {

  record Changes(
      @Valid
      Title title
  ) {

    record Title(
        @NotBlank
        String from
    ) {

    }
  }

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record PullRequest(
      @NotNull
      @Positive
      Integer id,
      @NotNull
      @Positive
      Integer number,
      @NotNull
      Boolean draft,
      @NotNull
      @Valid
      Author user,
      @PositiveOrZero
      Integer comments,
      @Valid
      @NotNull
      Branch base,
      @NotNull
      OffsetDateTime updatedAt,
      @Valid
      @NotNull
      Branch head,
      @NotNull
      State state,
      OffsetDateTime mergedAt,
      @NotBlank
      String htmlUrl,
      @NotBlank
      String title,
      String body
  ) {

    @EnumNaming(LowerCaseStrategy.class)
    enum State {
      OPEN,
      CLOSED
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record Branch(
        String sha,
        @NotBlank
        String ref,
        GitHubWebhookRepository repo
    ) {

    }

    record Author(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String login
    ) {

    }
  }

}
