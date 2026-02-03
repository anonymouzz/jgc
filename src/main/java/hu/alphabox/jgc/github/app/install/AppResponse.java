package hu.alphabox.jgc.github.app.install;

import java.util.List;
import java.util.Map;
import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
record AppResponse(
    Long id,
    String clientId,
    String name,
    String externalUrl,
    String htmlUrl,
    int installationsCount,
    Owner owner,
    Map<String, String> permissions,
    List<String> events
) {

  record Owner(
      Long id,
      String login,
      ActorType type
  ) {

  }

}
