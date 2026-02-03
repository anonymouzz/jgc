package hu.alphabox.jgc.github.app.install;

import java.util.List;
import java.util.Map;
import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
record InstallationResponse(
    Long id,
    String clientId,
    AccountResponse account,
    RepositorySelection repositorySelection,
    String accessTokenUrl,
    Long appId,
    Long targetId,
    ActorType targetType,
    Map<String, String> permissions,
    List<String> events
) {

  record AccountResponse(
      Long id,
      String login,
      ActorType type
  ) {

  }

}
