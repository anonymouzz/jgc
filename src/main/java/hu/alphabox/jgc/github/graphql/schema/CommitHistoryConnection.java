package hu.alphabox.jgc.github.graphql.schema;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommitHistoryConnection extends DefaultConnection<CommitEdge, Commit> {

  public CommitHistoryConnection(List<CommitEdge> edges, List<Commit> nodes, PageInfo pageInfo, int totalCount) {
    super(edges, nodes, pageInfo, totalCount);
  }
}
