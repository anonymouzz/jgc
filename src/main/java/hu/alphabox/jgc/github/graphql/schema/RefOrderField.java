package hu.alphabox.jgc.github.graphql.schema;

/**
 * Properties by which ref connections can be ordered.
 */
public enum RefOrderField {

  /**
   * Order refs by their alphanumeric name
   */
  ALPHABETICAL,

  /**
   * Order refs by underlying commit date if the ref prefix is refs/tags/
   */
  TAG_COMMIT_DATE
}
