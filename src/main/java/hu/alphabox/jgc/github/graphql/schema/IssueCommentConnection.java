package hu.alphabox.jgc.github.graphql.schema;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IssueCommentConnection extends DefaultConnection<IssueCommentEdge, Object> {

  public IssueCommentConnection(List<IssueCommentEdge> edges, List<Object> nodes, PageInfo pageInfo, int totalCount) {
    super(edges, nodes, pageInfo, totalCount);
  }
}
