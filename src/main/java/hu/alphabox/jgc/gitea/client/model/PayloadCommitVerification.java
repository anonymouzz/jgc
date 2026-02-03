package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;

public record PayloadCommitVerification(
    @Nullable
    String payload,
    @Nullable
    String reason,
    @Nullable
    String signature,
    @Nullable
    Boolean verified
) {

}

