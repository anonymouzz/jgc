package hu.alphabox.jgc.github.graphql.schema;

import java.net.URI;

/**
 * Represents an object which can take actions on GitHub. Typically a User or Bot.
 */
public interface Actor {

  /**
   * The username of the actor.
   */
  String login();

  /**
   * The HTTP URL for this actor.
   */
  URI url();

  /**
   * A URL pointing to the actor's public avatar.
   */
  URI avatarUrl();


}
