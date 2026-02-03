package hu.alphabox.jgc.github.graphql.schema;

/**
 * Information about pagination in a connection.
 *
 * @param startCursor     When paginating backwards, the cursor to continue.
 * @param endCursor       When paginating forwards, the cursor to continue.
 * @param hasNextPage     When paginating forwards, are there more items?
 * @param hasPreviousPage When paginating backwards, are there more items?
 */
public record PageInfo(
    String startCursor,
    String endCursor,
    boolean hasNextPage,
    boolean hasPreviousPage
) {

  public static PageInfo empty() {
    return new PageInfo(null, null, false, false);
  }

}
