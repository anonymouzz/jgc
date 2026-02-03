package hu.alphabox.jgc.github.graphql.schema;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ReviewRequestEdge extends DefaultEdge<ReviewRequest> {

  public ReviewRequestEdge(String cursor, ReviewRequest node) {
    super(cursor, node);
  }
}
