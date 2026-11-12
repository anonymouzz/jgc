package hu.alphabox.jgc.gitea.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("application.gitea.client")
@Validated
public record GiteaClientConfigurationProperties(
    @NotBlank
    String accessToken,
    @NotNull
    Integer concurrencyLimit,
    @NotNull
    Duration cacheTtl,
    @NotNull
    DataSize cacheMaxSize
) {

}
