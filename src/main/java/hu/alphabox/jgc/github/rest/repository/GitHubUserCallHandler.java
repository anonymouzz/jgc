package hu.alphabox.jgc.github.rest.repository;

import hu.alphabox.jgc.gitea.client.GiteaClient;
import hu.alphabox.jgc.gitea.client.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestController
@RequestMapping("/api/v3/users")
@RequiredArgsConstructor
class GitHubUserCallHandler {

  private final GiteaClient giteaClient;
  private final GitHubUserMapper mapper;

  @GetMapping("/{username}")
  ResponseEntity<GitHubUserResponse> user(@PathVariable String username) {
    try {
      log.info("Querying for user: {}", username);
      ResponseEntity<User> response = giteaClient.userGet(username);
      log.info("User found with name: {}", username);
      return ResponseEntity.ok(mapper.map(response.getBody()));
    } catch (HttpClientErrorException.NotFound _) {
      log.info("No user found with name: {}", username);
      return ResponseEntity.notFound().build();
    }
  }

}
