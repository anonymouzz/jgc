package hu.alphabox.jgc.gitea.client.model;

import org.jspecify.annotations.Nullable;
import tools.jackson.databind.EnumNamingStrategies.LowerCaseStrategy;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.EnumNaming;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Team(
    @Nullable
    String description,
    @Nullable
    Long id,
    @Nullable
    PermissionEnum permission
) {

  @EnumNaming(LowerCaseStrategy.class)
  public enum PermissionEnum {
    NONE,
    READ,
    WRITE,
    ADMIN,
    OWNER
  }

}

