package hu.alphabox.jgc.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
class TokenAuthenticationProvider implements AuthenticationProvider {

  private final String token;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (authentication.isAuthenticated()) {
      return TokenAuthenticationToken.authenticated();
    }

    if (token.contentEquals(((String) authentication.getCredentials()))) {
      return TokenAuthenticationToken.authenticated();
    }

    throw new BadCredentialsException("Bad credentials");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return TokenAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
