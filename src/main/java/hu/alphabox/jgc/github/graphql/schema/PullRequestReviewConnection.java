package hu.alphabox.jgc.github.graphql.schema;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PullRequestReviewConnection extends DefaultConnection<PullRequestReviewEdge, PullRequestReview> {

  public PullRequestReviewConnection(List<PullRequestReviewEdge> edges, List<PullRequestReview> nodes, PageInfo pageInfo, int totalCount) {
    super(edges, nodes, pageInfo, totalCount);
  }
}
