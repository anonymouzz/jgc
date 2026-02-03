package hu.alphabox.jgc.github.graphql.schema;

import java.net.URI;

/**
 * A user is an individual's account on GitHub that owns repositories and can make new content.
 *
 * @param login      The username of the user.
 * @param url        The HTTP URL for this user.
 * @param avatarUrl  A URL pointing to the user's public avatar.
 * @param name       The user's public profile name.
 * @param email      The user's publicly visible profile email.
 * @param databaseId Identifies the primary key from the database.
 */
public record User(
    String login,
    URI url,
    URI avatarUrl,
    String name,
    String email,
    Integer databaseId
) implements Actor, RequestedReviewer, RepositoryOwner {

}
