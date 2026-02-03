package hu.alphabox.jgc.github.graphql.schema;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RepositoryEdge extends DefaultEdge<Repository> {

  public RepositoryEdge(String cursor, Repository node) {
    super(cursor, node);
  }
}
