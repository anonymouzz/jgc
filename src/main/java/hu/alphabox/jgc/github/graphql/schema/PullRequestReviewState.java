package hu.alphabox.jgc.github.graphql.schema;

/**
 * The possible states of a pull request review.
 */
public enum PullRequestReviewState {

  /**
   * A review allowing the pull request to merge.
   */
  APPROVED,

  /**
   * A review blocking the pull request from merging.
   */
  CHANGES_REQUESTED,

  /**
   * An informational review.
   */
  COMMENTED,

  /**
   * A review that has been dismissed.
   */
  DISMISSED,

  /**
   * A review that has not yet been submitted.
   */
  PENDING
}
