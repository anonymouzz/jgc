package hu.alphabox.jgc.gitea.webhook;

import tools.jackson.databind.EnumNamingStrategies.LowerCaseStrategy;
import tools.jackson.databind.annotation.EnumNaming;

@EnumNaming(LowerCaseStrategy.class)
public enum GiteaEntityReferenceType {
  BRANCH,
  TAG
}
