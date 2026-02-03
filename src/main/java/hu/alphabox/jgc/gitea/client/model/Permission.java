package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;

public record Permission(
    @Nullable
    Boolean admin,
    @Nullable
    Boolean pull,
    @Nullable
    Boolean push
) {

}

