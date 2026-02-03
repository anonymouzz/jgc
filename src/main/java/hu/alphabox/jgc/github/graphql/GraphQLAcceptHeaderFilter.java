package hu.alphabox.jgc.github.graphql;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Replace "application/vnd.github.v3+json" with "application/json" for GraphQL requests.
 */
class GraphQLAcceptHeaderFilter extends OncePerRequestFilter {

  private static final String MEDIA_TYPE = "application/vnd.github.v3+json";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    filterChain.doFilter(new Wrapper(request), response);
  }

  private static class Wrapper extends HttpServletRequestWrapper {

    public Wrapper(HttpServletRequest request) {
      super(request);
    }

    @Override
    public String getHeader(String name) {
      String header = super.getHeader(name);
      if (HttpHeaders.ACCEPT.equals(name) && MEDIA_TYPE.equalsIgnoreCase(header)) {
        return MediaType.APPLICATION_JSON_VALUE;
      }
      return header;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
      Enumeration<String> headers = super.getHeaders(name);
      ArrayList<String> headersList = Collections.list(headers);
      if (HttpHeaders.ACCEPT.equals(name) && headersList.stream().anyMatch(MEDIA_TYPE::equalsIgnoreCase)) {
        headersList.removeIf(MEDIA_TYPE::equalsIgnoreCase);
        headersList.add(MediaType.APPLICATION_JSON_VALUE);
        return Collections.enumeration(headersList);
      }
      return headers;
    }
  }
}
