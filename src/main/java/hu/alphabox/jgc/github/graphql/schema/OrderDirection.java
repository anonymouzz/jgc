package hu.alphabox.jgc.github.graphql.schema;

/**
 * Possible directions in which to order a list of items when provided an `orderBy` argument.
 */
public enum OrderDirection {
  /**
   * Specifies an ascending order for a given `orderBy` argument.
   */
  ASC,
  /**
   * Specifies a descending order for a given `orderBy` argument.
   */
  DESC

}
