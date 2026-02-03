package hu.alphabox.jgc.gitea.client.model;

import java.util.List;
import java.util.Map;
import tools.jackson.databind.EnumNamingStrategies.LowerCaseStrategy;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.EnumNaming;
import tools.jackson.databind.annotation.JsonNaming;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateHookOption(
    Boolean active,
    Map<String, String> config,
    List<String> events,
    TypeEnum type
) {

  @EnumNaming(LowerCaseStrategy.class)
  public enum TypeEnum {
    GITEA
  }
}

