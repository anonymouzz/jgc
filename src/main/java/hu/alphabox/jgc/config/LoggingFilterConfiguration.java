package hu.alphabox.jgc.config;

import java.util.function.Predicate;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration(proxyBeanMethods = false)
class LoggingFilterConfiguration {

  @Bean
  CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeHeaders(true);
    filter.setIncludeClientInfo(true);
    filter.setIncludeQueryString(true);
    filter.setHeaderPredicate(Predicate.not(HttpHeaders.AUTHORIZATION::equalsIgnoreCase));
    return filter;
  }

  @Bean
  public FilterRegistrationBean<CommonsRequestLoggingFilter> loggingFilterRegistration(CommonsRequestLoggingFilter filter) {
    FilterRegistrationBean<CommonsRequestLoggingFilter> registration = new FilterRegistrationBean<>(filter);
    registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return registration;
  }

}
