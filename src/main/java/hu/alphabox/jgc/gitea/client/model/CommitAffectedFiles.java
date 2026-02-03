package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;

public record CommitAffectedFiles(
    @Nullable
    String filename,
    @Nullable
    String status
) {

}

