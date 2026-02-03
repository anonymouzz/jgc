package hu.alphabox.jgc.github.rest.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record GitHubBranchResponse(
    String name,
    Commit commit,
    @JsonProperty("protected")
    Boolean _protected
) {

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record Commit(
      String sha,
      URI url
  ) {

  }
}
