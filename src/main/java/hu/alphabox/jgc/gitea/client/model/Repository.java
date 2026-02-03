package hu.alphabox.jgc.gitea.client.model;

import java.time.OffsetDateTime;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Repository(
    @Nullable
    Boolean allowSquashMerge,
    @Nullable
    Boolean archived,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime archivedAt,
    @Nullable
    String cloneUrl,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime createdAt,
    @Nullable
    String defaultBranch,
    @Nullable
    String description,
    @Nullable
    Boolean fork,
    @Nullable
    Long forksCount,
    @Nullable
    String fullName,
    @Nullable
    Boolean hasIssues,
    @Nullable
    Boolean hasProjects,
    @Nullable
    Boolean hasWiki,
    @Nullable
    String htmlUrl,
    @Nullable
    Long id,
    @Nullable
    String language,
    @Nullable
    String languagesUrl,
    @Nullable
    String name,
    @Nullable
    Long openIssuesCount,
    @Nullable
    User owner,
    @Nullable
    Repository parent,
    @Nullable
    Permission permissions,
    @Nullable
    Boolean _private,
    @Nullable
    Long size,
    @Nullable
    String sshUrl,
    @Nullable
    Boolean template,
    List<String> topics,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime updatedAt,
    @Nullable
    String url,
    @Nullable
    Long watchersCount
) {

}

