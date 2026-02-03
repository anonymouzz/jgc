package hu.alphabox.jgc.github.graphql.schema;

import java.time.Instant;

/**
 * A review object for a given pull request.
 *
 * @param submittedAt Identifies when the Pull Request Review was submitted
 * @param state       Identifies the current state of the pull request review.
 * @param author      The actor who authored the comment.
 * @param repository  The repository associated with this node. // TODO Used for reference only
 * @param pullRequest Identifies the pull request associated with this pull request review. // TODO Used for reference only
 * @param databaseId  Identifies the primary key from the database.  // TODO Used for reference only
 */
public record PullRequestReview(
    Instant submittedAt,
    PullRequestReviewState state,
    Actor author,
    Repository repository,
    PullRequest pullRequest,
    Integer databaseId
) {

}
