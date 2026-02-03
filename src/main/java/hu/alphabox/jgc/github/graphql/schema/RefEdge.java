package hu.alphabox.jgc.github.graphql.schema;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RefEdge extends DefaultEdge<Ref> {

  public RefEdge(String cursor, Ref node) {
    super(cursor, node);
  }
}
