package hu.alphabox.jgc.github.graphql.schema;


/**
 * Represents the client's rate limit.
 *
 * @param cost The point cost for the current query counting against the rate limit.
 */
public record RateLimit(int cost) {

}
