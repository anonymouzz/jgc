package hu.alphabox.jgc.github.graphql.schema;

import java.net.URI;

/**
 * Represents an actor in a Git commit (ie. an author or committer).
 *
 * @param avatarUrl A URL pointing to the author's public avatar.
 * @param email     The email in the Git commit.
 * @param name      The name in the Git commit.
 * @param user      The GitHub user corresponding to the email field. Null if no such user exists.
 */
public record GitActor(
    URI avatarUrl,
    String email,
    String name,
    User user
) {

}
