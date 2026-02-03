package hu.alphabox.jgc.github.webhook;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record GitHubWebhookUser(
    Long id,
    String login,
    String name,
    String email
) {

}
