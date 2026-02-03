package hu.alphabox.jgc.github.rest.repository;

import java.net.URI;
import java.util.List;
import tools.jackson.databind.EnumNamingStrategies;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.EnumNaming;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
record GitHubPullRequestRequestedReviewersResponse(
    List<SimpleUser> users,
    List<Team> teams
) {

  @JsonNaming(SnakeCaseStrategy.class)
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

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record Team(
      Integer id,
      String nodeId,
      String name,
      String slug,
      String description,
      String privacy,
      String notificationSetting,
      String permission,
      Permissions permissions,
      URI url,
      URI htmlUrl,
      String membersUrl,
      URI repositoriesUrl,
      TeamType type,
      Integer organizationId,
      Integer enterpriseId,
      TeamSimple parent
  ) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record Permissions(
        Boolean pull,
        Boolean triage,
        Boolean push,
        Boolean maintain,
        Boolean admin
    ) {

    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record TeamSimple(
        Integer id,
        String nodeId,
        URI url,
        String membersUrl,
        String name,
        String description,
        String permission,
        String privacy,
        String notificationSetting,
        URI htmlUrl,
        URI repositoriesUrl,
        String slug,
        String ldapDn,
        TeamType type,
        Integer organizationId,
        Integer enterpriseId
    ) {

    }
  }


  @EnumNaming(EnumNamingStrategies.SnakeCaseStrategy.class)
  enum TeamType {
    ENTERPRISE,
    ORGANIZATION
  }
}
