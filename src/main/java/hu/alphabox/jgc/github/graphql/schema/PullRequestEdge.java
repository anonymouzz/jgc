package hu.alphabox.jgc.github.graphql.schema;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PullRequestEdge extends DefaultEdge<PullRequest> {

  public PullRequestEdge(String cursor, PullRequest node) {
    super(cursor, node);
  }
}
