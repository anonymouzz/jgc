package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;

public record Reference(
    @Nullable
    GitObject object,
    @Nullable
    String ref,
    @Nullable
    String url
) {

}

