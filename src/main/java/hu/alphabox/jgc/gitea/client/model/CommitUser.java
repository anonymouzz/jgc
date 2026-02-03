package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;

public record CommitUser(
    @Nullable
    String date,
    @Nullable
    String email,
    @Nullable
    String name
) {

}

