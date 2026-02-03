package hu.alphabox.jgc.github.rest.repository;

import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
record GitHubCreateRefRequest(
    String ref,
    String sha
) {

}
