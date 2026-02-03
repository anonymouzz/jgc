package hu.alphabox.jgc.github.rest.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.time.OffsetDateTime;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record GitHubPullRequestReviewCommentResponse(
    URI url,
    Long pullRequestReviewId,
    Long id,
    String nodeId,
    String diffHunk,
    String path,
    Integer position,
    Integer originalPosition,
    String commitId,
    String originalCommitId,
    Integer inReplyToId,
    SimpleUser user,
    String body,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    URI htmlUrl,
    URI pullRequestUrl,
    AuthorAssociation authorAssociation,
    Links _links,
    Integer startLine,
    Integer originalStartLine,
    StartSide startSide,
    Integer line,
    Integer originalLine,
    Side side,
    SubjectType subjectType,
    ReactionRollup reactions,
    String bodyHtml,
    String bodyText
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
      Boolean siteAdmin,
      String starredAt,
      String userViewType
  ) {

  }

  enum AuthorAssociation {
    @JsonProperty("COLLABORATOR") COLLABORATOR,
    @JsonProperty("CONTRIBUTOR") CONTRIBUTOR,
    @JsonProperty("FIRST_TIMER") FIRST_TIMER,
    @JsonProperty("FIRST_TIME_CONTRIBUTOR") FIRST_TIME_CONTRIBUTOR,
    @JsonProperty("MANNEQUIN") MANNEQUIN,
    @JsonProperty("MEMBER") MEMBER,
    @JsonProperty("NONE") NONE,
    @JsonProperty("OWNER") OWNER
  }

  enum StartSide {
    @JsonProperty("LEFT") LEFT,
    @JsonProperty("RIGHT") RIGHT
  }

  enum Side {
    @JsonProperty("LEFT") LEFT,
    @JsonProperty("RIGHT") RIGHT
  }

  enum SubjectType {
    @JsonProperty("line") LINE,
    @JsonProperty("file") FILE
  }

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record Links(
      Link self,
      Link html,
      @JsonProperty("pull_request") Link pullRequest
  ) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record Link(
        URI href
    ) {

    }
  }

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record ReactionRollup(
      URI url,
      Integer totalCount,
      @JsonProperty("+1") Integer plusOne,
      @JsonProperty("-1") Integer minusOne,
      Integer laugh,
      Integer confused,
      Integer heart,
      Integer hooray,
      Integer eyes,
      Integer rocket
  ) {

  }

}


