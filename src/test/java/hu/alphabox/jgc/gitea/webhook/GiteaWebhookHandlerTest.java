package hu.alphabox.jgc.gitea.webhook;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import hu.alphabox.jgc.github.webhook.WebhookService;
import hu.alphabox.jgc.github.webhook.WebhookSignature;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@SpringBootTest
@AutoConfigureMockMvc
class GiteaWebhookHandlerTest {

  @Autowired
  private MockMvcTester mvc;

  @Autowired
  private ResourceLoader resourceLoader;

  @MockitoBean
  private WebhookService service;

  @Test
  @WithMockUser
  void shouldParsePushEvent() throws IOException {
    Resource jsonToSend = resourceLoader.getResource("classpath:/request/webhook_push_event.json");

    byte[] body = jsonToSend.getContentAsByteArray();
    mvc.post()
        .uri("/api/gitea/webhook/1")
        .header("x-gitea-event", "push")
        .header("X-Gitea-Signature", WebhookSignature.generateSignature(body, "test-secret"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
        .assertThat()
        .hasStatusOk();

    verify(service).convertAndSend(any(GiteaWebhookPushEvent.class), anyInt());
  }

  @Test
  @WithMockUser
  void shouldParseBranchCreationEvent() throws IOException {
    Resource jsonToSend = resourceLoader.getResource("classpath:/request/webhook_branch_creation_event.json");

    byte[] body = jsonToSend.getContentAsByteArray();
    mvc.post()
        .uri("/api/gitea/webhook/1")
        .header("x-gitea-event", "create")
        .header("X-Gitea-Signature", WebhookSignature.generateSignature(body, "test-secret"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
        .assertThat()
        .hasStatusOk();

    verify(service).convertAndSend(any(GiteaWebhookBranchCreationEvent.class), anyInt());
  }

  @Test
  @WithMockUser
  void shouldParseBranchDeletionEvent() throws IOException {
    Resource jsonToSend = resourceLoader.getResource("classpath:/request/webhook_branch_deletion_event.json");

    byte[] body = jsonToSend.getContentAsByteArray();
    mvc.post()
        .uri("/api/gitea/webhook/1")
        .header("x-gitea-event", "delete")
        .header("X-Gitea-Signature", WebhookSignature.generateSignature(body, "test-secret"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
        .assertThat()
        .hasStatusOk();

    verify(service).convertAndSend(any(GiteaWebhookBranchDeletionEvent.class), anyInt());
  }

  @Test
  @WithMockUser
  void shouldParseRepositoryEvent() throws IOException {
    Resource jsonToSend = resourceLoader.getResource("classpath:/request/webhook_repository_event.json");

    byte[] body = jsonToSend.getContentAsByteArray();
    mvc.post()
        .uri("/api/gitea/webhook/1")
        .header("x-gitea-event", "repository")
        .header("X-Gitea-Signature", WebhookSignature.generateSignature(body, "test-secret"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
        .assertThat()
        .hasStatusOk();

    verify(service).convertAndSend(any(GiteaWebhookRepositoryEvent.class), anyInt());
  }

  @Test
  @WithMockUser
  void shouldParsePullRequestEvent() throws IOException {
    Resource jsonToSend = resourceLoader.getResource("classpath:/request/webhook_pull_request_opened_event.json");

    byte[] body = jsonToSend.getContentAsByteArray();
    mvc.post()
        .uri("/api/gitea/webhook/1")
        .header("x-gitea-event", "pull_request")
        .header("x-gitea-event-type", "pull_request")
        .header("X-Gitea-Signature", WebhookSignature.generateSignature(body, "test-secret"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
        .assertThat()
        .hasStatusOk();

    verify(service).convertAndSendPullRequest(any(GiteaWebhookPullRequestEvent.class), anyInt());
  }

  @Test
  @WithMockUser
  void shouldParsePullRequestReviewRequestEvent() throws IOException {
    Resource jsonToSend = resourceLoader.getResource("classpath:/request/webhook_pull_request_review_request_event.json");

    byte[] body = jsonToSend.getContentAsByteArray();
    mvc.post()
        .uri("/api/gitea/webhook/1")
        .header("x-gitea-event", "pull_request")
        .header("x-gitea-event-type", "pull_request_review_request")
        .header("X-Gitea-Signature", WebhookSignature.generateSignature(body, "test-secret"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
        .assertThat()
        .hasStatusOk();

    verify(service).convertAndSendPullRequest(any(GiteaWebhookPullRequestEvent.class), anyInt());
  }

  @Test
  @WithMockUser
  void shouldParsePullRequestCommentEvent() throws IOException {
    Resource jsonToSend = resourceLoader.getResource("classpath:/request/webhook_pull_request_comment_event.json");

    byte[] body = jsonToSend.getContentAsByteArray();
    mvc.post()
        .uri("/api/gitea/webhook/1")
        .header("x-gitea-event", "issue_comment")
        .header("x-gitea-event-type", "pull_request_comment")
        .header("X-Gitea-Signature", WebhookSignature.generateSignature(body, "test-secret"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
        .assertThat()
        .hasStatusOk();

    verify(service).convertAndSendIssueComment(any(GiteaWebhookPullRequestEvent.class), anyInt());
  }

  @Test
  @WithMockUser
  void shouldParsePullRequestReviewRejectedEvent() throws IOException {
    Resource jsonToSend = resourceLoader.getResource("classpath:/request/webhook_pull_request_review_rejected_event.json");

    byte[] body = jsonToSend.getContentAsByteArray();
    mvc.post()
        .uri("/api/gitea/webhook/1")
        .header("x-gitea-event", "pull_request_rejected")
        .header("x-gitea-event-type", "pull_request_review_rejected")
        .header("X-Gitea-Signature", WebhookSignature.generateSignature(body, "test-secret"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
        .assertThat()
        .hasStatusOk();

    verify(service).convertAndSendPullRequestReview(any(GiteaWebhookPullRequestEvent.class), anyInt(), eq("submitted"));
  }

  @Test
  @WithMockUser
  void shouldParsePullRequestReviewApprovedEvent() throws IOException {
    Resource jsonToSend = resourceLoader.getResource("classpath:/request/webhook_pull_request_review_approved_event.json");

    byte[] body = jsonToSend.getContentAsByteArray();
    mvc.post()
        .uri("/api/gitea/webhook/1")
        .header("x-gitea-event", "pull_request_approved")
        .header("x-gitea-event-type", "pull_request_review_approved")
        .header("X-Gitea-Signature", WebhookSignature.generateSignature(body, "test-secret"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
        .assertThat()
        .hasStatusOk();

    verify(service).convertAndSendPullRequestReview(any(GiteaWebhookPullRequestEvent.class), anyInt(), eq("submitted"));
  }

  @Test
  @WithMockUser
  void shouldParsePullRequestReviewCommentEvent() throws IOException {
    Resource jsonToSend = resourceLoader.getResource("classpath:/request/webhook_pull_request_review_comment_event.json");

    byte[] body = jsonToSend.getContentAsByteArray();
    mvc.post()
        .uri("/api/gitea/webhook/1")
        .header("x-gitea-event", "pull_request_comment")
        .header("x-gitea-event-type", "pull_request_review_comment")
        .header("X-Gitea-Signature", WebhookSignature.generateSignature(body, "test-secret"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
        .assertThat()
        .hasStatusOk();

    verify(service).convertAndSendPullRequestReview(any(GiteaWebhookPullRequestEvent.class), anyInt(), eq("edited"));
  }

}
