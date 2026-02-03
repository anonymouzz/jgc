package hu.alphabox.jgc.github.graphql.schema;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IssueCommentEdge extends DefaultEdge<Object> {

  public IssueCommentEdge(String cursor, Object node) {
    super(cursor, node);
  }
}
