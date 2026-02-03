package hu.alphabox.jgc.github.graphql.schema;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DeploymentStatusEdge extends DefaultEdge<DeploymentStatus> {

  public DeploymentStatusEdge(String cursor, DeploymentStatus node) {
    super(cursor, node);
  }
}
