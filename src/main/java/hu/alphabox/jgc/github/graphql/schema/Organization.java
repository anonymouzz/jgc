package hu.alphabox.jgc.github.graphql.schema;

import java.net.URI;

/**
 * An account on GitHub, with one or more owners, that has repositories, members and teams.
 *
 * @param login     The username of the actor.
 * @param url       The HTTP URL for this actor.
 * @param avatarUrl A URL pointing to the actor's public avatar.
 */
public record Organization(
    String login,
    URI url,
    URI avatarUrl
) implements Actor, RepositoryOwner {

}
