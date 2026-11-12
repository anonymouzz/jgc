package hu.alphabox.jgc.gitea.client;

import hu.alphabox.jgc.gitea.GiteaConfigurationProperties;
import io.micrometer.core.instrument.MeterRegistry;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({
    GiteaConfigurationProperties.class,
    GiteaClientConfigurationProperties.class
})
class GiteaClientConfiguration {

  private final GiteaConfigurationProperties properties;
  private final GiteaClientConfigurationProperties clientProperties;

  /** One instance for every client, so the limit is global. */
  @Bean
  GiteaConcurrencyLimitInterceptor giteaConcurrencyLimitInterceptor() {
    return new GiteaConcurrencyLimitInterceptor(clientProperties.concurrencyLimit());
  }

  @Bean
  @Primary
  GiteaClient giteaClient(Builder restClientBuilder, GiteaConcurrencyLimitInterceptor concurrencyLimit) {
    return createClient(restClientBuilder, GiteaClient.class, List.of(concurrencyLimit));
  }

  @Bean
  CachedGiteaClient cachedGiteaClient(
      Builder restClientBuilder,
      GiteaConcurrencyLimitInterceptor concurrencyLimit,
      MeterRegistry meterRegistry
  ) {
    GiteaResponseCacheInterceptor cache = new GiteaResponseCacheInterceptor(
        clientProperties.cacheTtl(),
        clientProperties.cacheMaxSize(),
        meterRegistry
    );
    // Order matters: cache first, so hits never wait for a permit.
    return createClient(restClientBuilder, CachedGiteaClient.class, List.of(cache, concurrencyLimit));
  }

  private <T extends GiteaClient> T createClient(Builder restClientBuilder, Class<T> type, List<ClientHttpRequestInterceptor> interceptors) {
    Builder builder = restClientBuilder
        .baseUrl(properties.baseUrl())
        .requestInterceptor(new GiteaTokenAuthenticationInterceptor(clientProperties.accessToken()));
    interceptors.forEach(builder::requestInterceptor);
    RestClient restClient = builder.build();

    RestClientAdapter adapter = RestClientAdapter.create(restClient);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

    return factory.createClient(type);
  }

  @Slf4j
  @RequiredArgsConstructor
  static class GiteaTokenAuthenticationInterceptor implements ClientHttpRequestInterceptor {

    private final String token;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
      request.getHeaders().setBearerAuth(token);

      log.debug(
          "Sending gitea request {} {} [headers: {}, body: {}]",
          request.getMethod(),
          request.getURI(),
          request.getHeaders(),
          new String(body)
      );
      return execution.execute(request, body);
    }
  }

}
