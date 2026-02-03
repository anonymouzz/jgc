package hu.alphabox.jgc.gitea.webhook;

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
public record GiteaWebhookPullRequestEvent(
    String action,
    @Positive
    Integer number,
    @NotNull
    @Valid
    PullRequest pullRequest,
    @NotNull
    @Valid
    GiteaWebhookRepository repository,
    Changes changes,
    Issue issue,
    Comment comment
) {

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record Comment(
      Long id,
      String body,
      GiteaWebhookUser user,
      String htmlUrl
  ) {

  }

  public record Issue(
      Long number
  ) {

  }

  public record Changes(
      @Valid
      Title title
  ) {

    public record Title(
        @NotBlank
        String from
    ) {

    }
  }

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record PullRequest(
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
      Integer reviewComments,
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
    public enum State {
      OPEN,
      CLOSED
    }

    public record Branch(
        String sha,
        @NotBlank
        String ref,
        GiteaWebhookRepository repo
    ) {

    }

    public record Author(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String login
    ) {

    }
  }

}
