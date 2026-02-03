package hu.alphabox.jgc.config.security;

import hu.alphabox.jgc.github.GitHubServerAccessTokenService;
import hu.alphabox.jgc.github.GitHubServerAccessTokenService.OpaqueToken;
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
class GitHubServerAccessTokenIntrospector implements OpaqueTokenIntrospector {

  private final GitHubAppInstallationManager installationManager;
  private final GitHubServerAccessTokenService tokenService;

  @Override
  public OAuth2AuthenticatedPrincipal introspect(String token) {
    if (installationManager.getAppInstallation().isEmpty()) {
      log.warn("GitHub App Installation not found for GitHub Server access token validation.");
      throw new BadOpaqueTokenException("Invalid token");
    }

    OpaqueToken opaqueToken = tokenService.getToken(token).orElseThrow(() -> {
      log.warn("GitHub Server access token not found: {}", token);
      return new BadOpaqueTokenException("Invalid token");
    });

    return new DefaultOAuth2AuthenticatedPrincipal(
        opaqueToken.installationId().toString(),
        Map.of(
            JwtClaimNames.ISS, opaqueToken.installationId(),
            JwtClaimNames.EXP, opaqueToken.expiresAt()
        ),
        Collections.emptyList()
    );
  }
}
