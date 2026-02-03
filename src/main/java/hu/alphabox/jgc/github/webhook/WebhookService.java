package hu.alphabox.jgc.github.webhook;

import hu.alphabox.jgc.gitea.client.GiteaClient;
import hu.alphabox.jgc.gitea.webhook.GiteaWebhookBranchCreationEvent;
import hu.alphabox.jgc.gitea.webhook.GiteaWebhookBranchDeletionEvent;
import hu.alphabox.jgc.gitea.webhook.GiteaWebhookPullRequestEvent;
import hu.alphabox.jgc.gitea.webhook.GiteaWebhookPullRequestEvent.PullRequest.Branch;
import hu.alphabox.jgc.gitea.webhook.GiteaWebhookPushEvent;
import hu.alphabox.jgc.gitea.webhook.GiteaWebhookRepositoryEvent;
import hu.alphabox.jgc.github.webhook.PullRequestEventMapper.BranchAuthorWrapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookService {

  private static final List<String> ALLOWED_PULL_REQUEST_ACTIONS = List.of(
      "opened",
      "reopened",
      "closed",
      "edited",
      "review_requested",
      "review_request_removed",
      "ready_for_review"
  );

  private final WebhookClient webhookClient;
  private final PushEventMapper pushEventMapper;
  private final BranchEventMapper branchEventMapper;
  private final RepositoryEventMapper repositoryEventMapper;
  private final PullRequestEventMapper pullRequestEventMapper;
  private final GiteaClient giteaClient;


  public void convertAndSend(GiteaWebhookPushEvent event, Integer installationId) {
    log.info("Sending GitHub 'push' event with installation id: {}", installationId);
    log.debug("Event: {}", event);
    webhookClient.sendEvent(pushEventMapper.map(event, installationId), "push");
  }

  public void convertAndSend(GiteaWebhookBranchCreationEvent event, Integer installationId) {
    log.info("Sending GitHub 'create' event with installation id: {}", installationId);
    log.debug("Event: {}", event);
    webhookClient.sendEvent(branchEventMapper.created(event, installationId), "create");
  }

  public void convertAndSend(GiteaWebhookBranchDeletionEvent event, Integer installationId) {
    log.info("Sending GitHub 'delete' event with installation id: {}", installationId);
    log.debug("Event: {}", event);
    webhookClient.sendEvent(branchEventMapper.deleted(event, installationId), "delete");
  }

  public void convertAndSend(GiteaWebhookRepositoryEvent event, Integer installationId) {
    log.info("Sending GitHub 'repository' event with installation id: {}", installationId);
    log.debug("Event: {}", event);
    webhookClient.sendEvent(repositoryEventMapper.map(event, installationId), "repository");
  }

  public void convertAndSendIssueComment(GiteaWebhookPullRequestEvent event, Integer installationId) {
    log.info("Sending GitHub 'issue_comment' event with installation id: {}", installationId);
    log.debug("Event: {}", event);
    webhookClient.sendEvent(pullRequestEventMapper.map(event, "edited", installationId), "issue_comment");
  }

  public void convertAndSendPullRequest(GiteaWebhookPullRequestEvent event, Integer installationId) {
    if (ALLOWED_PULL_REQUEST_ACTIONS.contains(event.action())) {
      log.info("Sending GitHub 'pull_request' event with installation id: {}", installationId);
      log.debug("Event: {}", event);
      Branch head = event.pullRequest().head();
      Branch base = event.pullRequest().base();
      webhookClient.sendEvent(pullRequestEventMapper.map(
          event,
          event.action(),
          installationId,
          new BranchAuthorWrapper(
              giteaClient.repoGetSingleCommit(
                  head.repo().owner().login(),
                  head.repo().name(), head.sha(),
                  false,
                  false,
                  false
              ).getBody().author(),
              giteaClient.repoGetSingleCommit(
                  base.repo().owner().login(),
                  base.repo().name(), base.sha(),
                  false,
                  false,
                  false
              ).getBody().author()
          )
      ), "pull_request");
    } else {
      log.warn("Not allowed pull request action: {}, ignoring.", event.action());
    }
  }

  public void convertAndSendPullRequestReview(GiteaWebhookPullRequestEvent event, Integer installationId, String action) {
    log.info("Sending GitHub 'pull_request_review' event with installation id: {}", installationId);
    log.debug("Event: {}", event);

    Branch head = event.pullRequest().head();
    Branch base = event.pullRequest().base();
    webhookClient.sendEvent(pullRequestEventMapper.map(event,
        action,
        installationId,
        new BranchAuthorWrapper(
            giteaClient.repoGetSingleCommit(
                head.repo().owner().login(),
                head.repo().name(), head.sha(),
                false,
                false,
                false
            ).getBody().author(),
            giteaClient.repoGetSingleCommit(
                base.repo().owner().login(),
                base.repo().name(), base.sha(),
                false,
                false,
                false
            ).getBody().author()
        )
    ), "pull_request_review");
  }
}
