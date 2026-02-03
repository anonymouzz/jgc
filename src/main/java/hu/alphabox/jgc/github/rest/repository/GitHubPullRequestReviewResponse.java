package hu.alphabox.jgc.github.rest.repository;

import java.net.URI;
import java.time.OffsetDateTime;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record GitHubPullRequestReviewResponse(
    Long id,
    String nodeId,
    SimpleUser user,
    String body,
    State state,
    URI htmlUrl,
    URI pullRequestUrl,
    Links _links,
    OffsetDateTime submittedAt,
    String commitId,
    AuthorAssociation authorAssociation
) {


  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record SimpleUser(
      String name,
      String email,
      String login,
      Long id,
      String nodeId,
      URI avatarUrl,
      String gravatarId,
      URI url,
      URI htmlUrl,
      URI followersUrl,
      String followingUrl,
      String gistsUrl,
      String starredUrl,
      URI subscriptionsUrl,
      URI organizationsUrl,
      URI reposUrl,
      String eventsUrl,
      URI receivedEventsUrl,
      String type,
      boolean siteAdmin,
      String starredAt,
      String userViewType
  ) {

  }


  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record Links(
      Html html,
      PullRequest pullRequest
  ) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record Html(String href) {

    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record PullRequest(String href) {

    }
  }

  enum State {
    APPROVED,
    CHANGES_REQUESTED,
    COMMENTED,
    DISMISSED,
    PENDING;
  }

  enum AuthorAssociation {
    COLLABORATOR,
    CONTRIBUTOR,
    FIRST_TIMER,
    FIRST_TIME_CONTRIBUTOR,
    MANNEQUIN,
    MEMBER,
    NONE,
    OWNER,
    UNKNOWN;
  }
}
