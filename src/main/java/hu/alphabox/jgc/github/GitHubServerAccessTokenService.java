package hu.alphabox.jgc.github;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import hu.alphabox.jgc.github.app.GitHubAppInstallation;
import hu.alphabox.jgc.github.app.GitHubAppInstallationInvalidClientException;
import hu.alphabox.jgc.github.app.GitHubAppInstallationInvalidGrantException;
import hu.alphabox.jgc.github.app.GitHubAppInstallationManager;
import hu.alphabox.jgc.github.app.GitHubAppInstallationMissingException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;


/**
 * @see <a
 * href="https://docs.github.com/en/apps/creating-github-apps/authenticating-with-a-github-app/generating-an-installation-access-token-for-a-github-app">
 * Generating Installation access token
 * </a>
 */
@Service
@RequiredArgsConstructor
public class GitHubServerAccessTokenService {

  private static final Duration TOKEN_VALIDITY_DURATION = Duration.ofHours(1);

  private final GitHubAppInstallationManager appInstallationManager;
  private final Cache<String, OpaqueToken> opaqueTokens = Caffeine.newBuilder().expireAfterWrite(TOKEN_VALIDITY_DURATION).build();

  public record OpaqueToken(
      String token,
      Instant expiresAt,
      Long installationId
  ) {

  }

  public OpaqueToken generateToken(Authentication authentication, Long installationId) {
    GitHubAppInstallation appInstallation = appInstallationManager.getAppInstallation().orElseThrow(GitHubAppInstallationMissingException::new);

    if (!(authentication instanceof JwtAuthenticationToken authenticationToken)) {
      throw new GitHubAppInstallationInvalidGrantException();
    }

    Jwt jwtToken = authenticationToken.getToken();
    if (
        !appInstallation.getId().toString().equals(jwtToken.getClaimAsString(JwtClaimNames.ISS))
            || appInstallation.getInstallation(installationId).isEmpty()
    ) {
      throw new GitHubAppInstallationInvalidClientException();
    }

    String token = GitHubStyleTokenGenerator.generateToken("ghs");

    OpaqueToken opaqueToken = new OpaqueToken(token, Instant.now().plus(TOKEN_VALIDITY_DURATION), installationId);
    opaqueTokens.put(token, opaqueToken);

    return opaqueToken;
  }

  public Optional<OpaqueToken> getToken(String token) {
    return Optional.ofNullable(opaqueTokens.getIfPresent(token));
  }

}
