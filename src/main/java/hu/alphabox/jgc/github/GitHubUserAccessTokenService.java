package hu.alphabox.jgc.github;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import hu.alphabox.jgc.github.app.GitHubAppInstallation;
import hu.alphabox.jgc.github.app.GitHubAppInstallationInvalidClientException;
import hu.alphabox.jgc.github.app.GitHubAppInstallationInvalidGrantException;
import hu.alphabox.jgc.github.app.GitHubAppInstallationManager;
import hu.alphabox.jgc.github.app.GitHubAppInstallationMissingException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 *
 * @see <a href="https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps#2-users-are-redirected-back-to-your-site-by-github">
 * OAuth temporary code expiration
 * </a>
 * @see <a
 * href="https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/token-expiration-and-revocation#user-token-expired-due-to-github-app-configuration">
 * User token expiration
 * </a>
 */
@Service
@RequiredArgsConstructor
public class GitHubUserAccessTokenService {

  private static final int TEMPORARY_CODE_LENGTH = 20;
  private static final Duration CODE_VALIDITY_DURATION = Duration.ofMinutes(10);
  private static final Duration TOKEN_VALIDITY_DURATION = Duration.ofHours(8);

  private final GitHubAppInstallationManager appInstallationManager;

  private final Cache<String, Instant> temporaryCodes = Caffeine.newBuilder().expireAfterAccess(CODE_VALIDITY_DURATION).build();
  private final Cache<String, OpaqueToken> opaqueTokens = Caffeine.newBuilder().expireAfterWrite(TOKEN_VALIDITY_DURATION).build();
  private final SecureRandom random = new SecureRandom();

  public record OpaqueToken(
      String token,
      Instant issuedAt
  ) {

  }

  public String generateTemporaryCode() {
    String code = random.ints(TEMPORARY_CODE_LENGTH, 0, 16)
        .mapToObj(Integer::toHexString)
        .collect(Collectors.joining());

    temporaryCodes.put(code, Instant.now());
    return code;
  }

  public String generateToken(String clientId, String clientSecret, String code) {
    GitHubAppInstallation appInstallation = appInstallationManager.getAppInstallation().orElseThrow(GitHubAppInstallationMissingException::new);
    if (!appInstallation.getClientId().equals(clientId) || !appInstallation.getClientSecret().equals(clientSecret)) {
      throw new GitHubAppInstallationInvalidClientException();
    }

    Instant expirationTime = temporaryCodes.getIfPresent(code);
    if (expirationTime == null || expirationTime.plus(CODE_VALIDITY_DURATION).isBefore(Instant.now())) {
      throw new GitHubAppInstallationInvalidGrantException();
    }
    temporaryCodes.invalidate(code);

    String token = GitHubStyleTokenGenerator.generateToken("ghu");
    opaqueTokens.put(token, new OpaqueToken(token, Instant.now()));

    return token;
  }

  public Optional<OpaqueToken> getToken(String token) {
    return Optional.ofNullable(opaqueTokens.getIfPresent(token));
  }

}
