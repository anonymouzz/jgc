package hu.alphabox.jgc.github.app;

import hu.alphabox.jgc.ApplicationProperties;
import java.io.File;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubAppInstallationManager implements InitializingBean {

  private final JsonMapper jsonMapper;
  private final ApplicationProperties applicationProperties;

  @Getter
  private final InstallationRegistrar installationRegistrar = InstallationRegistrar.empty();

  private GitHubAppInstallation installation;

  @Slf4j
  public static class InstallationRegistrar {

    private GitHubAppInstallation installation;

    public static InstallationRegistrar empty() {
      return new InstallationRegistrar();
    }

    public void mark(GitHubAppInstallation installation) {
      this.installation = installation;
      log.debug("New app installation marked for registration: {}", installation);
    }

    public Optional<GitHubAppInstallation> getInstallation() {
      return Optional.ofNullable(installation);
    }
  }


  @Override
  public void afterPropertiesSet() {
    File configFile = new File(applicationProperties.installationPath());
    if (!configFile.exists()) {
      return;
    }

    installation = jsonMapper.readValue(configFile, GitHubAppInstallation.class);
  }

  public void save() {
    File configFile = new File(applicationProperties.installationPath());
    jsonMapper.writerWithDefaultPrettyPrinter().writeValue(configFile, installation);
  }

  public Optional<GitHubAppInstallation> getAppInstallation() {
    return Optional.ofNullable(installation);
  }

  public void setAppInstallation(GitHubAppInstallation installation) {
    this.installation = installation;
    save();
  }


}
