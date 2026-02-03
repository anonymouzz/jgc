package hu.alphabox.jgc.github.graphql.schema;

/**
 * Ordering options for repository connections
 *
 * @param direction The ordering direction.
 * @param field     The field to order repositories by.
 */
public record RepositoryOrder(
    OrderDirection direction,
    RepositoryOrderField field
) {

}
