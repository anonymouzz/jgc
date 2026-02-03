package hu.alphabox.jgc.github.graphql.schema;

/**
 * The possible states of a pull request.
 */
public enum PullRequestState {

  /**
   * A pull request that has been closed without being merged.
   */
  CLOSED,

  /**
   * A pull request that has been closed by being merged.
   */
  MERGED,

  /**
   * A pull request that is still open.
   */
  OPEN

}
