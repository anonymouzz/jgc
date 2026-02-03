package hu.alphabox.jgc.github.rest.repository;

import java.net.URI;
import java.util.List;
import org.jspecify.annotations.Nullable;
import tools.jackson.databind.EnumNamingStrategies;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.EnumNaming;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record GitHubCommitResponse(
    URI url,
    String sha,
    String nodeId,
    URI htmlUrl,
    URI commentsUrl,
    CommitDetails commit,
    @Nullable
    User author,
    @Nullable
    User committer,
    List<Parent> parents,
    @Nullable
    Stats stats,
    @Nullable
    List<DiffEntry> files
) {

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record CommitDetails(
      URI url,
      @Nullable
      GitUser author,
      @Nullable
      GitUser committer,
      String message,
      int commentCount,
      Tree tree,
      Verification verification
  ) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record GitUser(
        @Nullable
        String name,
        @Nullable
        String email,
        @Nullable
        String date
    ) {

    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record Tree(
        String sha,
        URI url
    ) {

    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record Verification(
        boolean verified,
        String reason,
        @Nullable
        String payload,
        @Nullable
        String signature,
        @Nullable
        String verifiedAt
    ) {

    }
  }

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record User(
      @Nullable
      String name,
      @Nullable
      String email,
      String login,
      long id,
      String nodeId,
      URI avatarUrl,
      @Nullable
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
      @Nullable
      String starredAt,
      @Nullable
      String userViewType
  ) {

  }


  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record Parent(
      String sha,
      URI url,
      @Nullable
      URI htmlUrl
  ) {

  }

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record Stats(
      @Nullable
      Integer additions,
      @Nullable
      Integer deletions,
      @Nullable
      Integer total
  ) {

  }

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record DiffEntry(
      @Nullable
      String sha,
      String filename,
      Status status,
      int additions,
      int deletions,
      int changes,
      URI blobUrl,
      URI rawUrl,
      URI contentsUrl,
      @Nullable
      String patch,
      @Nullable
      String previousFilename
  ) {

    @EnumNaming(EnumNamingStrategies.SnakeCaseStrategy.class)
    enum Status {
      ADDED,
      REMOVED,
      MODIFIED,
      RENAMED,
      COPIED,
      CHANGED,
      UNCHANGED
    }
  }
}
