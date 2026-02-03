package hu.alphabox.jgc.github.graphql.schema;

import java.net.URI;
import java.time.Instant;

/**
 * Represents a Git commit.
 *
 * @param oid          The Git object ID
 * @param author       Authorship details of the commit.
 * @param authoredDate The datetime when this commit was authored.
 * @param changedFiles We recommend using the `changedFilesIfAvailable` field instead of `changedFiles`, as `changedFiles` will cause your request to return an
 *                     error if GitHub is unable to calculate the number of changed files. // TODO It's a required field, but some queries do not "fetch" it,
 *                     and we don't get it from Gitea, so as a "workaround", we allow to set it as 'null'
 * @param history      The linear commit history starting from (and including) this commit, in the same order as `git log`.
 * @param message      The Git commit message
 * @param url          The HTTP URL for this commit
 * @param repository   The Repository this commit belongs to
 */
public record Commit(
    String oid,
    GitActor author,
    Instant authoredDate,
    Integer changedFiles,
    CommitHistoryConnection history,
    String message,
    URI url,
    Repository repository
) implements GitObject {

}
