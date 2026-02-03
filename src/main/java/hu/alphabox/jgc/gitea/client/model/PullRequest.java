package hu.alphabox.jgc.gitea.client.model;

import java.time.OffsetDateTime;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record PullRequest(
    @Nullable
    Long additions,
    @Nullable
    PRBranchInfo base,
    @Nullable
    String body,
    @Nullable
    Long changedFiles,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime closedAt,
    @Nullable
    Long comments,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime createdAt,
    @Nullable
    Long deletions,
    @Nullable
    PRBranchInfo head,
    @Nullable
    String htmlUrl,
    @Nullable
    Long id,
    @Nullable
    String mergeCommitSha,
    @Nullable
    Boolean merged,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime mergedAt,
    @Nullable
    Milestone milestone,
    @Nullable
    Long number,
    List<User> requestedReviewers,
    @Nullable
    String state,
    @Nullable
    String title,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime updatedAt,
    @Nullable
    User user
) {

}

