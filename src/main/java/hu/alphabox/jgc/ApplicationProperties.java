package hu.alphabox.jgc;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("application")
public record ApplicationProperties(
    @NotBlank
    String baseUrl,
    @NotBlank
    String installationPath
) {

}
