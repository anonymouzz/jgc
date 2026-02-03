package hu.alphabox.jgc.github.rest.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
record GitHubContentTreeResponse(
    String type,
    Integer size,
    String name,
    String path,
    String sha,
    String content,
    URI url,
    URI gitUrl,
    URI htmlUrl,
    URI downloadUrl,
    List<Entry> entries,
    String encoding,
    @JsonProperty("_links")
    Links links
) {

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record Entry(
      String type,
      Integer size,
      String name,
      String path,
      String sha,
      URI url,
      URI gitUrl,
      URI htmlUrl,
      URI downloadUrl,
      Links _links
  ) {

  }

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  record Links(
      URI git,
      URI html,
      URI self
  ) {

  }
}
