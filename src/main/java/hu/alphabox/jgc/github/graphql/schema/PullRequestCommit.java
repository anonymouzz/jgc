package hu.alphabox.jgc.github.graphql.schema;

import java.net.URI;

/**
 * Represents a Git commit part of a pull request.
 *
 * @param commit       The Git commit object
 * @param id           The Node ID of the PullRequestCommit object
 * @param pullRequest  The pull request this commit belongs to
 * @param resourcePath The HTTP path for this pull request commit
 * @param url          The HTTP URL for this pull request commit
 */
public record PullRequestCommit(
    Commit commit,
    String id,
    PullRequest pullRequest,
    URI resourcePath,
    URI url
) {

}
