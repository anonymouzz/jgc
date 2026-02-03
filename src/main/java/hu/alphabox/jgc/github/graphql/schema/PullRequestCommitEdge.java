package hu.alphabox.jgc.github.graphql.schema;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PullRequestCommitEdge extends DefaultEdge<PullRequestCommit> {

  public PullRequestCommitEdge(String cursor, PullRequestCommit node) {
    super(cursor, node);
  }
}
