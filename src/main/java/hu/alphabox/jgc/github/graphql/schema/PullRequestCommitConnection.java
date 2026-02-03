package hu.alphabox.jgc.github.graphql.schema;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PullRequestCommitConnection extends DefaultConnection<PullRequestCommitEdge, PullRequestCommit> {

  public PullRequestCommitConnection(List<PullRequestCommitEdge> edges, List<PullRequestCommit> nodes, PageInfo pageInfo, int totalCount) {
    super(edges, nodes, pageInfo, totalCount);
  }
}
