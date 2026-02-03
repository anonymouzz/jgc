package hu.alphabox.jgc.github.rest.repository;

import hu.alphabox.jgc.gitea.client.GiteaClient;
import hu.alphabox.jgc.gitea.client.model.Reference;
import hu.alphabox.jgc.github.GitHubTokenFetcher;
import hu.alphabox.jgc.github.app.GitHubAppInstallationManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class GitHubRefCallHandlerTest {

  @Autowired
  private RestTestClient client;

  @Autowired
  private GitHubAppInstallationManager installationManager;

  @MockitoBean
  private GiteaClient giteaClient;

  @Test
  void shouldHandleEncodedGitRefRequest() throws URISyntaxException {
    ArgumentCaptor<String> refCaptor = ArgumentCaptor.forClass(String.class);

    Mockito
        .when(giteaClient.repoListGitRefs(Mockito.anyString(), Mockito.anyString(), refCaptor.capture()))
        .thenReturn(ResponseEntity.ok(List.of(new Reference(null, "refs/heads/release-1.0.0", null))));

    GitHubTokenFetcher tokenFetcher = new GitHubTokenFetcher(client, installationManager.getAppInstallation().orElseThrow());
    String serverToken = tokenFetcher.fetchServerToken();

    client.get()
        .uri(new URI("/api/v3/repos/TestOrg/TestRepo/git/ref/heads%2Frelease-1.0.0"))
        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(serverToken))
        .exchangeSuccessfully();

    Assertions.assertThat(refCaptor.getValue())
        .isEqualTo("heads/release-1.0.0");
  }

}
