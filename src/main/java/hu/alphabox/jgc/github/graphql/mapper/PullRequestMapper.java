package hu.alphabox.jgc.github.graphql.mapper;

import hu.alphabox.jgc.TypeConversionMapper;
import hu.alphabox.jgc.config.MapperCentralConfig;
import hu.alphabox.jgc.gitea.GiteaPageResponse;
import hu.alphabox.jgc.gitea.client.model.PRBranchInfo;
import hu.alphabox.jgc.gitea.client.model.PullReview;
import hu.alphabox.jgc.gitea.client.model.User;
import hu.alphabox.jgc.github.graphql.mapper.PullRequestMapper.ReviewRequestMapper;
import hu.alphabox.jgc.github.graphql.schema.Commit;
import hu.alphabox.jgc.github.graphql.schema.IssueCommentConnection;
import hu.alphabox.jgc.github.graphql.schema.PageInfo;
import hu.alphabox.jgc.github.graphql.schema.PullRequest;
import hu.alphabox.jgc.github.graphql.schema.PullRequestCommit;
import hu.alphabox.jgc.github.graphql.schema.PullRequestCommitConnection;
import hu.alphabox.jgc.github.graphql.schema.PullRequestCommitEdge;
import hu.alphabox.jgc.github.graphql.schema.PullRequestConnection;
import hu.alphabox.jgc.github.graphql.schema.PullRequestEdge;
import hu.alphabox.jgc.github.graphql.schema.PullRequestReview;
import hu.alphabox.jgc.github.graphql.schema.PullRequestReviewConnection;
import hu.alphabox.jgc.github.graphql.schema.PullRequestReviewEdge;
import hu.alphabox.jgc.github.graphql.schema.PullRequestReviewState;
import hu.alphabox.jgc.github.graphql.schema.PullRequestState;
import hu.alphabox.jgc.github.graphql.schema.Ref;
import hu.alphabox.jgc.github.graphql.schema.Repository;
import hu.alphabox.jgc.github.graphql.schema.ReviewRequest;
import hu.alphabox.jgc.github.graphql.schema.ReviewRequestConnection;
import hu.alphabox.jgc.github.graphql.schema.ReviewRequestEdge;
import java.util.Collections;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

@Mapper(
    config = MapperCentralConfig.class,
    uses = {
        GitActorMapper.class,
        RepositoryMapper.class,
        TypeConversionMapper.class,
        ReviewRequestMapper.class
    }
)
public interface PullRequestMapper {

  default PullRequestConnection map(
      String cursorPage,
      GiteaPageResponse<hu.alphabox.jgc.gitea.client.model.PullRequest> giteaResponse,
      Repository repository
  ) {
    List<PullRequest> nodes = nodes(giteaResponse.data(), repository);
    PageInfo pageInfo = PageInfoMapper.map(cursorPage, giteaResponse);

    return new PullRequestConnection(
        nodes.stream().map(node -> edge(node, pageInfo.endCursor())).toList(),
        nodes,
        pageInfo,
        giteaResponse.totalCount()
    );
  }


  @Mapping(target = "url", source = "htmlUrl")
  @Mapping(target = "baseRefName", source = "base.label")
  @Mapping(target = "headRefName", source = "head.label")
  @Mapping(target = "headRef", source = "head")
  @Mapping(target = "mergeCommit", source = "mergeCommitSha")
  @Mapping(target = "author", source = "user", qualifiedByName = "actor")
  @Mapping(target = "reviewRequests", source = "requestedReviewers")
  @Mapping(target = "repository", expression = "java(repository)")
  @Mapping(target = "state", source = ".")
  PullRequest node(hu.alphabox.jgc.gitea.client.model.PullRequest pullRequest, @Context Repository repository);

  List<PullRequest> nodes(List<hu.alphabox.jgc.gitea.client.model.PullRequest> pullRequests, @Context Repository repository);

  PullRequestEdge edge(PullRequest node, String cursor);

  @Mapping(target = "id", source = "sha")
  @Mapping(target = "name", source = "label")
  @Mapping(target = "repository", source = "repo")
  @Mapping(target = "target", source = ".")
  Ref ref(PRBranchInfo branchInfo);

  @Mapping(target = "oid", source = "sha")
  @Mapping(target = "repository", source = "repo")
  Commit target(PRBranchInfo branchInfo);

  @Mapping(target = "oid", source = "sha")
  @Mapping(target = "repository", expression = "java(repository)")
  Commit mergeCommit(String sha, @Context Repository repository);

  default IssueCommentConnection comments(Long comments) {
    return new IssueCommentConnection(
        Collections.emptyList(),
        Collections.emptyList(),
        PageInfo.empty(),
        comments == null ? 0 : comments.intValue()
    );
  }

  default PullRequestState state(hu.alphabox.jgc.gitea.client.model.PullRequest pullRequest) {
    if (pullRequest.state() == null) {
      return null;
    }

    return switch (pullRequest.state()) {
      case "open" -> PullRequestState.OPEN;
      case "closed" -> Boolean.TRUE.equals(pullRequest.merged()) ? PullRequestState.MERGED : PullRequestState.CLOSED;
      default -> throw new IllegalStateException("Unknown pull request state: " + pullRequest.state());
    };
  }

  @Mapper(
      config = MapperCentralConfig.class,
      uses = GitActorMapper.class
  )
  interface ReviewRequestMapper {

    @Mapping(target = "requestedReviewer", source = "requestedReviewer", qualifiedByName = "user")
    ReviewRequest node(User requestedReviewer);

    List<ReviewRequest> nodes(List<User> requestedReviewers);

    @Mapping(target = "node", source = "requestedReviewer")
    ReviewRequestEdge edge(User requestedReviewer);

    List<ReviewRequestEdge> edges(List<User> requestedReviewers);

    default ReviewRequestConnection map(List<User> users) {
      return new ReviewRequestConnection(
          edges(users),
          nodes(users),
          null, // TODO
          users != null ? users.size() : 0
      );
    }
  }

  @Mapper(
      config = MapperCentralConfig.class,
      uses = {
          GitActorMapper.class,
          RepositoryMapper.class,
          TypeConversionMapper.class
      }
  )
  interface PullRequestReviewMapper {

    default PullRequestReviewConnection map(
        String cursorPage,
        GiteaPageResponse<PullReview> giteaResponse,
        PullRequest pullRequest
    ) {
      List<PullRequestReview> nodes = nodes(giteaResponse.data(), pullRequest);
      PageInfo pageInfo = PageInfoMapper.map(cursorPage, giteaResponse);

      return new PullRequestReviewConnection(
          nodes.stream().map(node -> edge(node, pageInfo.endCursor())).toList(),
          nodes,
          pageInfo,
          giteaResponse.totalCount()
      );
    }

    List<PullRequestReview> nodes(List<PullReview> pullReviews, @Context PullRequest pullRequest);

    @Mapping(target = "databaseId", source = "id")
    @Mapping(target = "pullRequest", expression = "java(pullRequest)")
    @Mapping(target = "repository", expression = "java(pullRequest.getRepository())")
    @Mapping(target = "author", source = "user", qualifiedByName = "actor")
    PullRequestReview node(PullReview pullReview, @Context PullRequest pullRequest);

    PullRequestReviewEdge edge(PullRequestReview node, String cursor);


    @ValueMapping(target = "APPROVED", source = "APPROVED")
    @ValueMapping(target = "PENDING", source = "PENDING")
    @ValueMapping(target = "PENDING", source = "REQUEST_REVIEW")
    @ValueMapping(target = "COMMENTED", source = "COMMENT")
    @ValueMapping(target = "CHANGES_REQUESTED", source = "REQUEST_CHANGES")
    @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MappingConstants.ANY_REMAINING)
    PullRequestReviewState state(String state);
  }

  @Mapper(
      config = MapperCentralConfig.class,
      uses = {
          TypeConversionMapper.class,
          CommitMapper.class
      }
  )
  interface PullRequestCommitMapper {

    default PullRequestCommitConnection map(
        String cursorPage,
        GiteaPageResponse<hu.alphabox.jgc.gitea.client.model.Commit> giteaResponse,
        Repository repository
    ) {
      List<PullRequestCommit> nodes = nodes(giteaResponse.data(), repository);
      PageInfo pageInfo = PageInfoMapper.map(cursorPage, giteaResponse);

      return new PullRequestCommitConnection(
          nodes.stream().map(node -> edge(node, pageInfo.endCursor())).toList(),
          nodes,
          pageInfo,
          giteaResponse.totalCount()
      );
    }

    List<PullRequestCommit> nodes(List<hu.alphabox.jgc.gitea.client.model.Commit> commits, @Context Repository repository);

    @Mapping(target = "url", ignore = true)
    @Mapping(target = "commit", source = ".")
    PullRequestCommit node(hu.alphabox.jgc.gitea.client.model.Commit commit, @Context Repository repository);

    PullRequestCommitEdge edge(PullRequestCommit node, String cursor);
  }


}

