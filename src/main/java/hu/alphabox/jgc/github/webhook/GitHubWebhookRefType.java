package hu.alphabox.jgc.github.webhook;

import tools.jackson.databind.EnumNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.EnumNaming;

@EnumNaming(SnakeCaseStrategy.class)
enum GitHubWebhookRefType {
  BRANCH,
  TAG
}
