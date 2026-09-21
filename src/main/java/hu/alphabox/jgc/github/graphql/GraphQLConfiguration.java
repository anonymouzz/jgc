package hu.alphabox.jgc.github.graphql;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.graphql.autoconfigure.ConditionalOnGraphQlSchema;
import org.springframework.boot.graphql.autoconfigure.GraphQlProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.TimeoutWebGraphQlInterceptor;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@ConditionalOnGraphQlSchema
@EnableConfigurationProperties(GraphQlProperties.class)
class GraphQLConfiguration {

  private final GraphQlProperties graphQlProperties;

  @Bean
  TimeoutWebGraphQlInterceptor timeoutWebGraphQlInterceptor(
      // GitHub GraphQL default is 10s; Atlassian backfill against Gitea often needs more.
      // Env: APPLICATION_GRAPHQL_TIMEOUTSECONDS (seconds). Unset => 10.
      @Value("${application.graphql.timeout-seconds:10}") long timeoutSeconds
  ) {
    return new TimeoutWebGraphQlInterceptor(Duration.ofSeconds(timeoutSeconds));
  }

  @Bean
  FilterRegistrationBean<GraphQLAcceptHeaderFilter> graphQLAcceptHeaderFilter() {
    FilterRegistrationBean<GraphQLAcceptHeaderFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new GraphQLAcceptHeaderFilter());
    registrationBean.addUrlPatterns(graphQlProperties.getHttp().getPath());
    return registrationBean;
  }

}
