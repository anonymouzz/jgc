package hu.alphabox.jgc.github.graphql.schema;

import java.util.Collections;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DeploymentConnection extends DefaultConnection<DeploymentEdge, Deployment> {

  public DeploymentConnection(List<DeploymentEdge> edges, List<Deployment> nodes, PageInfo pageInfo, int totalCount) {
    super(edges, nodes, pageInfo, totalCount);
  }

  public static DeploymentConnection empty() {
    return new DeploymentConnection(
        Collections.emptyList(),
        Collections.emptyList(),
        PageInfo.empty(),
        0
    );
  }
}
