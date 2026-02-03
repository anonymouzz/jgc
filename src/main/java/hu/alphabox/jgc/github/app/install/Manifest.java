package hu.alphabox.jgc.github.app.install;

import java.util.List;
import java.util.Map;
import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
record Manifest(
    String name,
    String url,
    HookAttributes hookAttributes,
    String redirectUrl,
    String callbackUrl,
    String setupUrl,
    String description,
    Boolean _public,
    List<String> defaultEvents,
    Map<String, String> defaultPermissions,
    Boolean requestOauthOnInstall,
    Boolean setupOnUpdate
) {

  record HookAttributes(
      String url,
      Boolean active
  ) {

  }

}
