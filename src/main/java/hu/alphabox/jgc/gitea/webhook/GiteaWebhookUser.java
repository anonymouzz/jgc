package hu.alphabox.jgc.gitea.webhook;

import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GiteaWebhookUser(
    Long id,
    String email,
    @NotBlank
    String login,
    @NotBlank
    String username
) {

}
