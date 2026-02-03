package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;

public record CommitStats(
    @Nullable
    Long additions,
    @Nullable
    Long deletions,
    @Nullable
    Long total
) {

}

