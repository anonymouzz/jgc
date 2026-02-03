package hu.alphabox.jgc.gitea;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("application.gitea")
@Validated
public record GiteaConfigurationProperties(
    @NotBlank
    String baseUrl
) {

}
