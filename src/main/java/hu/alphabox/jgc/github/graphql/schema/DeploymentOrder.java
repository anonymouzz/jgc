package hu.alphabox.jgc.github.graphql.schema;

/**
 * Ordering options for deployment connections
 *
 * @param direction The ordering direction.
 * @param field     The field to order deployments by.
 */
public record DeploymentOrder(
    OrderDirection direction,
    DeploymentOrderField field
) {

}
