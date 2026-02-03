package hu.alphabox.jgc.github.graphql.schema;

/**
 * Ways in which lists of issues can be ordered upon return.
 *
 * @param direction The direction in which to order issues by the specified field.
 * @param field     The field in which to order issues by.
 */
public record IssueOrder(
    OrderDirection direction,
    IssueOrderField field
) {

}
