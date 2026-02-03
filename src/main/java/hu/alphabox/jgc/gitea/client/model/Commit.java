package hu.alphabox.jgc.gitea.client.model;

import java.time.OffsetDateTime;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Commit(
    @Nullable
    User author,
    @Nullable
    RepoCommit commit,
    @Nullable
    User committer,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime created,
    List<CommitAffectedFiles> files,
    @Nullable
    String htmlUrl,
    List<CommitMeta> parents,
    @Nullable
    String sha,
    @Nullable
    CommitStats stats,
    @Nullable
    String url
) {

}

