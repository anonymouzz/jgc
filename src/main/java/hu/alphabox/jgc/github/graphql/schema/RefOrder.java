package hu.alphabox.jgc.github.graphql.schema;

/**
 * Ways in which lists of git refs can be ordered upon return.
 *
 * @param direction The direction in which to order refs by the specified field.
 * @param field     The field in which to order refs by.
 */
public record RefOrder(
    OrderDirection direction,
    RefOrderField field
) {

}
