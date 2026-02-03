package hu.alphabox.jgc.github.graphql.schema;

import lombok.Data;

@Data
abstract class DefaultEdge<T> implements Edge<T> {

  private final String cursor;
  private final T node;

}
