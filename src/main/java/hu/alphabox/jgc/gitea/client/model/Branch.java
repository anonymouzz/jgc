package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Branch(
    @Nullable
    PayloadCommit commit,
    @Nullable
    String name,
    @Nullable
    Boolean _protected
) {

}

