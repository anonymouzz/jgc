package hu.alphabox.jgc.github.graphql.schema;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DeploymentStatusConnection extends DefaultConnection<DeploymentStatusEdge, DeploymentStatus> {

  public DeploymentStatusConnection(List<DeploymentStatusEdge> edges, List<DeploymentStatus> nodes, PageInfo pageInfo, int totalCount) {
    super(edges, nodes, pageInfo, totalCount);
  }
}
