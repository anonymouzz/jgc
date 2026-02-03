package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;

public record CommitMeta(
    @Nullable
    String sha,
    @Nullable
    String url
) {

}

