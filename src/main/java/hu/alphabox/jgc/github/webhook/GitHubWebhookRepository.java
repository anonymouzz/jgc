package hu.alphabox.jgc.github.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.OffsetDateTime;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record GitHubWebhookRepository(
    @NotNull
    @Positive
    Long id,
    @NotBlank
    String name,
    @NotBlank
    String fullName,
    @NotNull
    @Valid
    GitHubWebhookUser owner,
    @NotBlank
    String htmlUrl,
    @NotNull
    OffsetDateTime updatedAt
) {

}
