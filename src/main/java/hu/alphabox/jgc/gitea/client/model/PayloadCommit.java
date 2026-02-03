package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;

public record PayloadCommit(
    @Nullable
    String id,
    @Nullable
    String url
) {

}

