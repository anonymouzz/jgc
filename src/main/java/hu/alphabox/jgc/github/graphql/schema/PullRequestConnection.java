package hu.alphabox.jgc.github.graphql.schema;

import java.util.Collections;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PullRequestConnection extends DefaultConnection<PullRequestEdge, PullRequest> {

  public PullRequestConnection(List<PullRequestEdge> edges, List<PullRequest> nodes, PageInfo pageInfo, int totalCount) {
    super(edges, nodes, pageInfo, totalCount);
  }

  public static PullRequestConnection empty() {
    return new PullRequestConnection(
        Collections.emptyList(),
        Collections.emptyList(),
        PageInfo.empty(),
        0
    );
  }

}
