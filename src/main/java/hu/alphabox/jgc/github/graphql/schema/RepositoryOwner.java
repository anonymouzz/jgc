package hu.alphabox.jgc.github.graphql.schema;

import java.net.URI;

/**
 * Represents an owner of a Repository.
 */
public interface RepositoryOwner {

  /**
   * The username used to login.
   */
  String login();

  /**
   * A URL pointing to the owner's public avatar.
   */
  URI avatarUrl();

}
