package hu.alphabox.jgc.github.graphql.schema;

/**
 * Properties by which repository connections can be ordered.
 */
public enum RepositoryOrderField {
  /**
   * Order repositories by creation time
   */
  CREATED_AT,

  /**
   * Order repositories by name
   */
  NAME,

  /**
   * Order repositories by push time
   */
  PUSHED_AT,

  /**
   * Order repositories by number of stargazers
   */
  STARGAZERS,

  /**
   * Order repositories by update time
   */
  UPDATED_AT
}
