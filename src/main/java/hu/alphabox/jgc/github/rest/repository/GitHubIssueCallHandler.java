package hu.alphabox.jgc.github.rest.repository;

import hu.alphabox.jgc.gitea.client.GiteaClient;
import hu.alphabox.jgc.gitea.client.model.EditIssueCommentOption;
import hu.alphabox.jgc.gitea.client.model.EditIssueOption;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v3/repos/{owner}/{repository}/issues")
@RequiredArgsConstructor
class GitHubIssueCallHandler {

  private final GiteaClient giteaClient;

  record IssueUpdateRequest(
      @NotBlank
      String body
  ) {

  }

  @PatchMapping("/{index}")
  void updateIssue(
      @PathVariable String owner,
      @PathVariable String repository,
      @PathVariable Long index,
      @RequestBody @Valid IssueUpdateRequest request
  ) {
    log.info("Update gitea issue with index: {} in {}/{}, ", index, owner, repository);
    log.debug("New issue body: {}", request.body);
    giteaClient.issueEditIssue(
        owner,
        repository,
        index,
        new EditIssueOption(request.body)
    );
  }

  record IssueCommentUpdateRequest(
      @NotBlank
      String body
  ) {

  }

  @PatchMapping("/comments/{id}")
  void updateIssueComment(
      @PathVariable String owner,
      @PathVariable String repository,
      @PathVariable Long id,
      @RequestBody @Valid IssueCommentUpdateRequest request
  ) {
    log.info("Update gitea issue comment with id: {} in {}/{}, ", id, owner, repository);
    log.debug("New issue comment body: {}", request.body);
    giteaClient.issueEditComment(
        owner,
        repository,
        id,
        new EditIssueCommentOption(request.body)
    );
  }
}
