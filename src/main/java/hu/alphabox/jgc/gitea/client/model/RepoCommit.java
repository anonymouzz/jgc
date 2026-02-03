package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;

public record RepoCommit(
    @Nullable
    CommitUser author,
    @Nullable
    CommitUser committer,
    @Nullable
    String message,
    @Nullable
    CommitMeta tree,
    @Nullable
    String url,
    @Nullable
    PayloadCommitVerification verification
) {

}

