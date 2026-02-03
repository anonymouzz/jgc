package hu.alphabox.jgc.config.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.StringUtils;

class GitHubUserBearerTokenResolver implements BearerTokenResolver {

  private static final String BEARER_TOKEN_HEADER_NAME = HttpHeaders.AUTHORIZATION;
  private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("^(token|Bearer) (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);


  @Override
  public String resolve(HttpServletRequest request) {
    String authorization = request.getHeader(BEARER_TOKEN_HEADER_NAME);
    if (!StringUtils.startsWithIgnoreCase(authorization, "token") && !StringUtils.startsWithIgnoreCase(authorization, "Bearer")) {
      return null;
    }

    Matcher matcher = AUTHORIZATION_PATTERN.matcher(authorization);
    if (!matcher.matches()) {
      BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
      throw new OAuth2AuthenticationException(error);
    }

    return matcher.group("token");

  }
}
