package hu.alphabox.jgc.github.app.install;

import hu.alphabox.jgc.ErrorPageController;
import hu.alphabox.jgc.github.app.GitHubAppInstallation;
import hu.alphabox.jgc.github.app.GitHubAppInstallation.Installation;
import hu.alphabox.jgc.github.app.GitHubAppInstallationManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
class InstallationPageController {

  private final InstallationService service;
  private final GitHubAppInstallationManager installationManager;

  @GetMapping("/jira-gitea-connector/installations/new")
  String showOrganizationSelector(Model model) {

    Optional<GitHubAppInstallation> optionalInstallation = installationManager.getAppInstallation();
    if (optionalInstallation.isEmpty()) {
      return "redirect: " + ErrorPageController.generateRedirectUrl(
          "Unexpected error",
          "An unexpected error has occurred while processing your request"
      );
    }

    record OrganizationModel(
        long id,
        String name,
        String avatarUrl
    ) {

    }

    List<OrganizationModel> organizations = service.getOrganizationsForUser()
        .stream()
        .map(organization -> new OrganizationModel(
            organization.id(),
            organization.name(),
            organization.avatarUrl()
        ))
        .toList();

    GitHubAppInstallation appInstallation = optionalInstallation.get();
    Installation installation = appInstallation.resolveInstallationForConfiguration();
    appInstallation.addInstallation(installation);

    model.addAttribute("organizations", organizations);
    model.addAttribute("installationId", installation.getId());

    return "gitea-installation-chooser";
  }
}
