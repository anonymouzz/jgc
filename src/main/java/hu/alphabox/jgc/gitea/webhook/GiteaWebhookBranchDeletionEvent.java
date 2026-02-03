package hu.alphabox.jgc.gitea.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GiteaWebhookBranchDeletionEvent(
    @NotBlank
    String ref,
    @NotNull
    GiteaEntityReferenceType refType,
    @NotNull
    @Valid
    GiteaWebhookRepository repository
) {

}
