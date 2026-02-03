package hu.alphabox.jgc.github.graphql.schema;

/**
 * An edge in a connection. Represents a node of data T and the cursor for that node.
 */
public interface Edge<T> {

  /**
   * A cursor for use in pagination.
   */
  String getCursor();

  /**
   * The item at the end of the edge.
   */
  T getNode();

}
