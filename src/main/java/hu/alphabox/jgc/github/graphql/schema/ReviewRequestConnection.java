package hu.alphabox.jgc.github.graphql.schema;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ReviewRequestConnection extends DefaultConnection<ReviewRequestEdge, ReviewRequest> {

  public ReviewRequestConnection(List<ReviewRequestEdge> edges, List<ReviewRequest> nodes, PageInfo pageInfo, int totalCount) {
    super(edges, nodes, pageInfo, totalCount);
  }
}
