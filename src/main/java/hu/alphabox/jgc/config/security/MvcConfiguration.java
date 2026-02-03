package hu.alphabox.jgc.config.security;

import hu.alphabox.jgc.config.LogTopic;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
class MvcConfiguration implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/robots.txt", "/favicon.ico")
        .addResourceLocations("classpath:/static/");
  }

  @Bean
  public SimpleUrlHandlerMapping unmappedUrlHandlerMapping() {
    return new SimpleUrlHandlerMapping(Map.of("/**", new UnmappedUrlHandler()), Ordered.LOWEST_PRECEDENCE);
  }

  @Slf4j(topic = LogTopic.REQUEST_FAILURE)
  private static class UnmappedUrlHandler implements HandlerFunction<ServerResponse> {

    @Override
    public ServerResponse handle(ServerRequest request) {
      log.warn("Unmapped request.");
      RequestLogger.log(request.servletRequest(), log);
      return ServerResponse.notFound().build();
    }
  }
}
