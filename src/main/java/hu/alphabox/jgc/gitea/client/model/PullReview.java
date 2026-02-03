package hu.alphabox.jgc.gitea.client.model;

import java.time.OffsetDateTime;
import org.jspecify.annotations.Nullable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PullReview(
    @Nullable
    String body,
    @Nullable
    String commitId,
    @Nullable
    String htmlUrl,
    @Nullable
    Long id,
    @Nullable
    String pullRequestUrl,
    @Nullable
    String state,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime submittedAt,
    @Nullable
    Team team,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime updatedAt,
    @Nullable
    User user
) {

}

