package hu.alphabox.jgc.github.graphql.schema;

import java.util.List;
import lombok.Data;

@Data
abstract class DefaultConnection<E extends Edge<T>, T> implements Connection<E, T> {

  private final List<E> edges;
  private final List<T> nodes;
  private final PageInfo pageInfo;
  private final int totalCount;

}
