package hu.alphabox.jgc.config.security;

import java.util.Collections;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class TokenAuthenticationToken extends AbstractAuthenticationToken {

  private final String token;

  private TokenAuthenticationToken(String token) {
    super(Collections.singletonList(new SimpleGrantedAuthority("TOKEN")));
    this.token = token;
  }

  public static TokenAuthenticationToken unauthenticated(String token) {
    TokenAuthenticationToken authenticationToken = new TokenAuthenticationToken(token);
    authenticationToken.setAuthenticated(false);
    return authenticationToken;
  }

  public static TokenAuthenticationToken authenticated() {
    TokenAuthenticationToken authenticationToken = new TokenAuthenticationToken(null);
    authenticationToken.setAuthenticated(true);
    return authenticationToken;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }
}
