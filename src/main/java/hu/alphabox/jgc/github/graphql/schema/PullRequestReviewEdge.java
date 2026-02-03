package hu.alphabox.jgc.github.graphql.schema;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PullRequestReviewEdge extends DefaultEdge<PullRequestReview> {

  public PullRequestReviewEdge(String cursor, PullRequestReview node) {
    super(cursor, node);
  }
}
