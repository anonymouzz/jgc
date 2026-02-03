package hu.alphabox.jgc.github.graphql.schema;


import lombok.Data;

/**
 * Represents a Git reference.
 */
@Data
public class Ref {

  /*
   * The Node ID of the Ref object
   */
  private String id;

  /*
   * The ref name.
   */
  private String name;

  /*
   * The ref's prefix, such as `refs/heads/` or `refs/tags/`.
   */
  private String prefix;

  /*
   * The object the ref points to. Returns null when object does not exist.
   */
  private GitObject target;

  /*
   * A list of pull requests with this ref as the head ref.
   */
  private PullRequestConnection associatedPullRequests;

  /*
   * The repository the ref belongs to.
   */
  private Repository repository;

}
