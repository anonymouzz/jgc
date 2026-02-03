package hu.alphabox.jgc.github.graphql.schema;

/**
 * Properties by which issue connections can be ordered.
 */
public enum IssueOrderField {
  /**
   * Order issues by comment count
   */
  COMMENTS,

  /**
   * Order issues by creation time
   */
  CREATED_AT,

  /**
   * Order issues by update time
   */
  UPDATED_AT
}
