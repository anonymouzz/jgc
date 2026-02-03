package hu.alphabox.jgc.github.app.install;

import tools.jackson.databind.EnumNamingStrategies.LowerCaseStrategy;
import tools.jackson.databind.annotation.EnumNaming;

@EnumNaming(LowerCaseStrategy.class)
enum RepositorySelection {
  ALL,
  SELECTED
}
