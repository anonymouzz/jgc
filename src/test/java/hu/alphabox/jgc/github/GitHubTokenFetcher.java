package hu.alphabox.jgc.github;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTClaimsSet.Builder;
import com.nimbusds.jwt.SignedJWT;
import hu.alphabox.jgc.github.app.GitHubAppInstallation;
import hu.alphabox.jgc.github.app.GitHubAppInstallation.Installation;
import java.time.Instant;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@RequiredArgsConstructor
public class GitHubTokenFetcher {

  private final RestTestClient client;
  private final GitHubAppInstallation appInstallation;

  /**
   * Fetch a server token for the first installation.
   *
   * @return GH server token
   */
  public String fetchServerToken() {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record AccessTokenResponse(String token, Instant expiresAt) {

    }

    JWT jwt = generateJWT();
    Installation installation = this.appInstallation.getInstallations().iterator().next();

    AccessTokenResponse response = client.post()
        .uri("/api/v3/app/installations/{installation-id}/access_tokens", installation.getId())
        .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(jwt.serialize()))
        .exchangeSuccessfully()
        .returnResult(AccessTokenResponse.class)
        .getResponseBody();

    Assertions.assertThat(response)
        .isNotNull()
        .hasNoNullFieldsOrProperties();

    return response.token;
  }

  private JWT generateJWT() {
    JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
        .type(JOSEObjectType.JWT)
        .build();

    Instant now = Instant.now();
    JWTClaimsSet claimsSet = new Builder()
        .issuer(appInstallation.getId().toString())
        .issueTime(Date.from(now))
        .expirationTime(Date.from(now.plusSeconds(600)))
        .build();

    SignedJWT jwt = new SignedJWT(header, claimsSet);

    try {
      RSASSASigner signer = new RSASSASigner(JWK.parseFromPEMEncodedObjects(appInstallation.getPrivateKey()).toRSAKey());
      jwt.sign(signer);
    } catch (JOSEException ex) {
      throw new IllegalStateException(ex);
    }

    return jwt;
  }
}
