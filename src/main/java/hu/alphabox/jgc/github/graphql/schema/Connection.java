package hu.alphabox.jgc.github.graphql.schema;

import java.util.List;

/**
 * Provides a standard mechanism for slicing and paginating the result set.
 */
public interface Connection<E extends Edge<T>, T> {

  List<E> getEdges();

  List<T> getNodes();

  PageInfo getPageInfo();

  int getTotalCount();

}
