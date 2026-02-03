package hu.alphabox.jgc.github.app.install;

import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
record AppConversionsResponse(
    Long id,
    String name,
    String clientId,
    String clientSecret,
    String webhookSecret,
    String pem
) {

}
