package hu.alphabox.jgc.gitea.client.model;

import java.time.OffsetDateTime;
import org.jspecify.annotations.Nullable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record Milestone(
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime closedAt,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime createdAt,
    @Nullable
    String description,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime dueOn,
    @Nullable
    Long id,
    @Nullable
    String state,
    @Nullable
    String title,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime updatedAt
) {

}

