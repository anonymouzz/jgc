package hu.alphabox.jgc.gitea.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.OffsetDateTime;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GiteaWebhookRepository(
    @NotNull
    @Positive
    Long id,
    @NotBlank
    String name,
    @NotBlank
    String fullName,
    @NotBlank
    String htmlUrl,
    @NotNull
    @Valid
    GiteaWebhookUser owner,
    OffsetDateTime updatedAt
) {

}
