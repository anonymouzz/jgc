package hu.alphabox.jgc.github;

import hu.alphabox.jgc.gitea.GiteaConfigurationProperties;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
class GitRedirectHandler {

  private final GiteaConfigurationProperties giteaProperties;

  @GetMapping("/{organization}/{repository}/tree/{branch}")
  ResponseEntity<Void> branchRedirect(
      HttpServletRequest request,
      @PathVariable String branch,
      @PathVariable String organization,
      @PathVariable String repository
  ) {
    return ResponseEntity.status(HttpStatus.FOUND)
        .location(
            UriComponentsBuilder.fromUriString(giteaProperties.baseUrl())
                .path(URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8))
                .build()
                .toUri()
        )
        .build();
  }

}
