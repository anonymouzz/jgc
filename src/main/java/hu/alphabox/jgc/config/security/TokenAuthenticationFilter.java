package hu.alphabox.jgc.config.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public TokenAuthenticationFilter() {
    super(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/login/token"));
    setAuthenticationConverter(new TokenAuthenticationConverter());
  }

  private static class TokenAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
      String token = request.getParameter("token");
      token = token != null ? token.trim() : "";
      return TokenAuthenticationToken.unauthenticated(token);
    }
  }


}
