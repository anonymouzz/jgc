package hu.alphabox.jgc.gitea.client.model;

import java.time.OffsetDateTime;
import org.jspecify.annotations.Nullable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record User(
    @Nullable
    String avatarUrl,
    @Nullable
    @DateTimeFormat(iso = ISO.DATE_TIME)
    OffsetDateTime created,
    @Nullable
    String description,
    @Nullable
    String email,
    @Nullable
    String fullName,
    @Nullable
    String htmlUrl,
    @Nullable
    Long id,
    @Nullable
    Boolean isAdmin,
    @Nullable
    String location,
    @Nullable
    String login
) {

}

