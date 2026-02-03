package hu.alphabox.jgc.github.graphql.schema;

import java.net.URI;
import java.time.Instant;
import lombok.Data;

/**
 * A repository pull request.
 */
@Data
public class PullRequest {

  /*
   * Identifies the pull request number.
   */
  private int number;

  /*
   * The Node ID of the PullRequest object
   */
  private String id;

  /*
   * Identifies the state of the pull request.
   */
  private PullRequestState state;

  /*
   * The date and time that the pull request was merged.
   */
  private Instant mergedAt;

  /*
   * Identifies the date and time when the object was created.
   */
  private Instant createdAt;

  /*
   * Identifies the date and time when the object was last updated.
   */
  private Instant updatedAt;

  /*
   * Identifies the pull request title.
   */
  private String title;

  /*
   * The body as Markdown.
   */
  private String body;

  /*
   * The HTTP URL for this pull request.
   */
  private URI url;

  /*
   * Identifies the name of the base Ref associated with the pull request, even if the ref has been deleted.
   */
  private String baseRefName;

  /*
   * Identifies the name of the head Ref associated with the pull request, even if the ref has been deleted.
   */
  private String headRefName;

  /*
   * Identifies the head Ref associated with the pull request.
   */
  private Ref headRef;

  /*
   * A list of comments associated with the pull request.
   */
  private IssueCommentConnection comments;

  /*
   * The actor who authored the comment.
   */
  private Actor author;

  /*
   * The commit that was created when this pull request was merged.
   */
  private Commit mergeCommit;

  /*
   * A list of commits present in this pull request's head branch not present in the base branch.
   */
  private PullRequestCommitConnection commits;

  /*
   * A list of reviews associated with the pull request.
   */
  private PullRequestReviewConnection reviews;

  /*
   * A list of review requests associated with the pull request.
   */
  private ReviewRequestConnection reviewRequests;

  /*
   * The repository associated with this node.
   */
  // TODO Used for reference only
  private Repository repository;

}
