package hu.alphabox.jgc.config.security;

import hu.alphabox.jgc.github.app.GitHubAppInstallation;
import hu.alphabox.jgc.github.app.GitHubAppInstallationManager;
import hu.alphabox.jgc.github.webhook.WebhookSignature;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.HttpStatusAccessDeniedHandler;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
class GiteaWebhookSecurityFilter extends OncePerRequestFilter {

  private static final String GITEA_SIGNATURE_HEADER = "X-Gitea-Signature";

  private final AccessDeniedHandler accessDeniedHandler = new HttpStatusAccessDeniedHandler(HttpStatus.FORBIDDEN);

  private final GitHubAppInstallationManager installationManager;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Optional<GitHubAppInstallation> optionalAppInstallation = installationManager.getAppInstallation();
    if (optionalAppInstallation.isEmpty()) {
      log.warn("No GitHub app installation found, denying webhook request from: {}", request.getRemoteAddr());
      accessDeniedHandler.handle(request, response, new AuthorizationDeniedException("Missing application registration."));
      return;
    }
    GitHubAppInstallation appInstallation = optionalAppInstallation.get();

    String signature = request.getHeader(GITEA_SIGNATURE_HEADER);
    if (signature == null || signature.isBlank()) {
      log.warn("No signature header found in webhook request from: {}", request.getRemoteAddr());
      accessDeniedHandler.handle(request, response, new AuthorizationDeniedException("Missing Gitea signature"));
      return;
    }

    HttpServletRequest requestToUse = request;
    if (!(request instanceof CachedBodyHttpServletRequest)) {
      requestToUse = new CachedBodyHttpServletRequest(request);
    }

    byte[] content = requestToUse.getInputStream().readAllBytes();
    String generatedSignature = WebhookSignature.generateSignature(content, appInstallation.getWebhookSecret());
    if (!generatedSignature.equals(signature)) {
      log.warn("Invalid signature header in webhook request from: {}", request.getRemoteAddr());
      accessDeniedHandler.handle(request, response, new AuthorizationDeniedException("Gitea signature mismatch"));
      return;
    }

    filterChain.doFilter(requestToUse, response);
  }

}
