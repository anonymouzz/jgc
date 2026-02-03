package hu.alphabox.jgc.github.webhook;

import hu.alphabox.jgc.TypeConversionMapper;
import hu.alphabox.jgc.config.MapperCentralConfig;
import hu.alphabox.jgc.gitea.client.model.User;
import hu.alphabox.jgc.gitea.webhook.GiteaWebhookPullRequestEvent;
import hu.alphabox.jgc.github.webhook.GitHubWebhookPullRequestEvent.PullRequest;
import hu.alphabox.jgc.github.webhook.GitHubWebhookPullRequestEvent.PullRequest.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapperCentralConfig.class,
    uses = {
        TypeConversionMapper.class,
        InnerGitHubObjectMapper.class
    }
)
interface PullRequestEventMapper {

  record BranchAuthorWrapper(
      User head,
      User base
  ) {

  }

  @Mapping(target = "installation.id", source = "installationId")
  @Mapping(target = "action", source = "action")
  @Mapping(target = "pullRequest", expression = "java(map(event.pullRequest(), branchAuthorWrapper))")
  GitHubWebhookPullRequestEvent map(
      GiteaWebhookPullRequestEvent event,
      String action,
      Integer installationId,
      BranchAuthorWrapper branchAuthorWrapper
  );

  @Mapping(target = "installation.id", source = "installationId")
  @Mapping(target = "action", source = "action")
  GitHubWebhookIssueCommentEvent map(
      GiteaWebhookPullRequestEvent event,
      String action,
      Integer installationId
  );

  @Mapping(target = "head", expression = "java(mapBranch(pullRequest.head(), branchAuthorWrapper.head()))")
  @Mapping(target = "base", expression = "java(mapBranch(pullRequest.base(), branchAuthorWrapper.base()))")
  @Mapping(target = "comments", source = "pullRequest.reviewComments")
  PullRequest map(
      GiteaWebhookPullRequestEvent.PullRequest pullRequest,
      BranchAuthorWrapper branchAuthorWrapper
  );

  Branch mapBranch(GiteaWebhookPullRequestEvent.PullRequest.Branch branch, User user);
}
