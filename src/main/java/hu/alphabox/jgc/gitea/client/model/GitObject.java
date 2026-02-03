package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;

public record GitObject(
    @Nullable
    String sha,
    @Nullable
    String type,
    @Nullable
    String url
) {

}

