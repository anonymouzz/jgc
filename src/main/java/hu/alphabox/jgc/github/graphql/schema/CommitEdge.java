package hu.alphabox.jgc.github.graphql.schema;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommitEdge extends DefaultEdge<Commit> {

  public CommitEdge(String cursor, Commit node) {
    super(cursor, node);
  }
}
