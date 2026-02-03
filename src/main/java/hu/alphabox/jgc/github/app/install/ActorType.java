package hu.alphabox.jgc.github.app.install;

import tools.jackson.databind.EnumNamingStrategies.UpperCamelCaseStrategy;
import tools.jackson.databind.annotation.EnumNaming;

@EnumNaming(UpperCamelCaseStrategy.class)
enum ActorType {
  ORGANIZATION,
  USER
}
