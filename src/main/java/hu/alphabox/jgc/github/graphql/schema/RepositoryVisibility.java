package hu.alphabox.jgc.github.graphql.schema;

/**
 * The repository's visibility level.
 */
public enum RepositoryVisibility {
  /**
   * The repository is visible only to users in the same enterprise.
   */
  INTERNAL,

  /**
   * The repository is visible only to those with explicit access.
   */
  PRIVATE,

  /**
   * The repository is visible to everyone.
   */
  PUBLIC

}
