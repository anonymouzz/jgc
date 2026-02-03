package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;
import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record ContentsResponse(
    @Nullable
    FileLinksResponse links,
    @Nullable
    String content,
    @Nullable
    String downloadUrl,
    @Nullable
    String encoding,
    @Nullable
    String gitUrl,
    @Nullable
    String htmlUrl,
    @Nullable
    String name,
    @Nullable
    String path,
    @Nullable
    String sha,
    @Nullable
    Long size,
    @Nullable
    String type,
    @Nullable
    String url
) {

}

