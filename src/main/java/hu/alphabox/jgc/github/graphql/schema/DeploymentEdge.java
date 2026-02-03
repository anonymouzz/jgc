package hu.alphabox.jgc.github.graphql.schema;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DeploymentEdge extends DefaultEdge<Deployment> {

  public DeploymentEdge(String cursor, Deployment node) {
    super(cursor, node);
  }
}
