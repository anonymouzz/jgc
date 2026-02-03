package hu.alphabox.jgc.github.graphql.schema.scalar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration(proxyBeanMethods = false)
class ScalarConfiguration {

  @Bean
  public RuntimeWiringConfigurer runtimeWiringConfigurer() {
    return wiringBuilder -> wiringBuilder
        .scalar(GraphQLScalar.DATE_TIME)
        .scalar(GraphQLScalar.GIT_TIMESTAMP)
        .scalar(GraphQLScalar.GIT_OBJECT_ID)
        .scalar(GraphQLScalar.URI)
        .scalar(GraphQLScalar.VOID);
  }
}
