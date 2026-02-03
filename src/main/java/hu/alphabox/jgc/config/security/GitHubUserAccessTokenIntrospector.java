package hu.alphabox.jgc.config.security;

import hu.alphabox.jgc.github.GitHubUserAccessTokenService;
import hu.alphabox.jgc.github.GitHubUserAccessTokenService.OpaqueToken;
import hu.alphabox.jgc.github.app.GitHubAppInstallationManager;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class GitHubUserAccessTokenIntrospector implements OpaqueTokenIntrospector {

  private final GitHubAppInstallationManager installationManager;
  private final GitHubUserAccessTokenService tokenService;

  @Override
  public OAuth2AuthenticatedPrincipal introspect(String token) {
    if (installationManager.getAppInstallation().isEmpty()) {
      log.warn("GitHub App Installation not found for GitHub User access token validation.");
      throw new BadOpaqueTokenException("Invalid token");
    }

    OpaqueToken opaqueToken = tokenService.getToken(token).orElseThrow(() -> {
      log.warn("GitHub User access token not found: {}", token);
      return new BadOpaqueTokenException("Invalid token");
    });

    return new DefaultOAuth2AuthenticatedPrincipal(
        "user",
        Map.of(JwtClaimNames.IAT, opaqueToken.issuedAt()),
        Collections.emptyList()
    );
  }
}
