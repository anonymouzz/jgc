package hu.alphabox.jgc.github.app.install;

import java.util.List;
import java.util.Map;
import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
record UserInstallationsResponse(
    Integer totalCount,
    List<Installation> installations
) {

  @JsonNaming(SnakeCaseStrategy.class)
  record Installation(
      Long id,
      Account account,
      Long appId,
      Long targetId,
      ActorType targetType,
      Map<String, String> permissions,
      List<String> events,
      RepositorySelection repositorySelection
  ) {

    @JsonNaming(SnakeCaseStrategy.class)
    record Account(
        Long id,
        String login,
        ActorType type
    ) {

    }
  }
}
