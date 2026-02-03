package hu.alphabox.jgc.github.rest.repository;

import hu.alphabox.jgc.LinkHeaderProcessor;
import hu.alphabox.jgc.gitea.client.GiteaClient;
import hu.alphabox.jgc.gitea.client.model.PullReview;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/api/v3/repos/{owner}/{repo}/pulls")
@RequiredArgsConstructor
class GitHubPullRequestCallHandler {

  private final GiteaClient giteaClient;
  private final GitHubPullRequestMapper mapper;

  @GetMapping("/{pull_number}/reviews")
  ResponseEntity<List<GitHubPullRequestReviewResponse>> reviews(
      HttpServletRequest request,
      @PathVariable String owner,
      @PathVariable String repo,
      @PathVariable("pull_number") Long pullNumber,
      @RequestParam(value = "per_page", defaultValue = "30") Integer limit,
      @RequestParam(value = "page", defaultValue = "1") Integer page
  ) {
    try {
      log.info("Querying for pull request reviews for pull number: {} in {}/{} at page {}", pullNumber, owner, repo, page);
      ResponseEntity<List<PullReview>> response = giteaClient.repoListPullReviews(owner, repo, pullNumber, page, limit);
      if (!response.hasBody()) {
        log.info("No pull request reviews found for pull number: {} in {}/{} at page {}", pullNumber, owner, repo, page);
        return ResponseEntity.ok(Collections.emptyList());
      }

      int totalCount = Integer.parseInt(Objects.requireNonNullElse(response.getHeaders().get("X-Total-Count"), List.of("0")).getFirst());

      URI baseURI = UriComponentsBuilder.newInstance()
          .scheme(request.getScheme())
          .host(request.getServerName())
          .port(request.getServerPort())
          .path(request.getRequestURI())
          .build()
          .toUri();

      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.LINK, LinkHeaderProcessor.create(baseURI, limit, page, totalCount).toHeader());

      log.info("Successfully returned pull request reviews for pull number: {} in {}/{} at page {}", pullNumber, owner, repo, page);
      return ResponseEntity.ok()
          .headers(headers)
          .body(mapper.reviews(response.getBody()));
    } catch (HttpClientErrorException.NotFound _) {
      log.info("No pull request reviews found for pull number: {} in {}/{} at page {}", pullNumber, owner, repo, page);
      return ResponseEntity.ok(Collections.emptyList());
    }
  }

  @GetMapping("/{pull_number}/requested_reviewers")
  GitHubPullRequestRequestedReviewersResponse requestedReviewers(
      @PathVariable String owner,
      @PathVariable String repo,
      @PathVariable("pull_number") Long pullNumber
  ) {
    try {
      log.info("Querying for pull request requested reviewers for pull number: {} in {}/{}", pullNumber, owner, repo);
      ResponseEntity<List<PullReview>> response = giteaClient.repoListPullReviews(owner, repo, pullNumber, null, null);
      if (!response.hasBody()) {
        log.info("No pull request requested reviewers found for pull number: {} in {}/{}", pullNumber, owner, repo);
        return new GitHubPullRequestRequestedReviewersResponse(Collections.emptyList(), Collections.emptyList());
      }
      log.info("Pull request requested reviewers found for pull number: {} in {}/{}", pullNumber, owner, repo);
      return mapper.map(response.getBody());
    } catch (HttpClientErrorException.NotFound _) {
      log.info("No pull request requested reviewers found for pull number: {} in {}/{}", pullNumber, owner, repo);
      return new GitHubPullRequestRequestedReviewersResponse(Collections.emptyList(), Collections.emptyList());
    }
  }

  @GetMapping("/{pull_number}/comments")
  List<GitHubPullRequestReviewCommentResponse> comments(
      @PathVariable String owner,
      @PathVariable String repo,
      @PathVariable("pull_number") Long pullNumber
  ) {
    return Collections.emptyList();
  }

}
