package hu.alphabox.jgc.github.app.install;

import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
record MembershipResponse(
    String state,
    String role,
    User user,
    Organization organization
) {

  record User(
      String login,
      Long id,
      ActorType type
  ) {

  }

  record Organization(
      String login,
      Long id
  ) {

  }
}
