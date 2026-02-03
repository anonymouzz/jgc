package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;

public record FileLinksResponse(
    @Nullable
    String git,
    @Nullable
    String html,
    @Nullable
    String self
) {

}

