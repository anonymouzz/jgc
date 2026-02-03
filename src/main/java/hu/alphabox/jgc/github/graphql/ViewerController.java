package hu.alphabox.jgc.github.graphql;

import hu.alphabox.jgc.config.LogTopic;
import hu.alphabox.jgc.gitea.GiteaPageResponse;
import hu.alphabox.jgc.gitea.client.GiteaClient;
import hu.alphabox.jgc.gitea.client.model.Repository;
import hu.alphabox.jgc.gitea.client.model.SearchResults;
import hu.alphabox.jgc.github.app.GitHubAppInstallation;
import hu.alphabox.jgc.github.app.GitHubAppInstallation.Installation;
import hu.alphabox.jgc.github.app.GitHubAppInstallationManager;
import hu.alphabox.jgc.github.app.GitHubAppInstallationMissingException;
import hu.alphabox.jgc.github.graphql.mapper.GiteaOrderMapper;
import hu.alphabox.jgc.github.graphql.mapper.RepositoryMapper;
import hu.alphabox.jgc.github.graphql.schema.RepositoryAffiliation;
import hu.alphabox.jgc.github.graphql.schema.RepositoryConnection;
import hu.alphabox.jgc.github.graphql.schema.RepositoryOrder;
import hu.alphabox.jgc.github.graphql.schema.User;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Slf4j(topic = LogTopic.BACKFILL_DATA_FETCHING)
@Controller
@RequiredArgsConstructor
class ViewerController {

  private final GitHubAppInstallationManager installationManager;
  private final RepositoryMapper repositoryMapper;
  private final GiteaClient giteaClient;

  @QueryMapping
  User viewer() {
    return new User(null, null, null, null, null, null);
  }

  @SchemaMapping(typeName = "User", field = "repositories")
  RepositoryConnection repositories(
      Authentication authentication,
      User user,
      @Argument Integer first,
      @Argument String after,
      @Argument RepositoryOrder orderBy,
      @Argument List<RepositoryAffiliation> affiliations,
      @Argument List<RepositoryAffiliation> ownerAffiliations
  ) {
    GitHubAppInstallation appInstallation = installationManager.getAppInstallation().orElseThrow(GitHubAppInstallationMissingException::new);
    Installation installation = appInstallation.getInstallation(Long.valueOf(authentication.getName())).orElseThrow(RuntimeException::new);

    int page = Integer.parseInt(Objects.requireNonNullElse(after, "1"));
    GiteaPageResponse<Repository> response = GiteaPageResponse.of(
        giteaClient.repoSearch(
            null,
            false,
            true,
            installation.getOrganizationId(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            //There’s no need for a general check, since the same user is always the one querying and having access to the repository.
            ownerAffiliations.size() == 1 && ownerAffiliations.getFirst() == RepositoryAffiliation.OWNER,
            orderBy == null ? null : GiteaOrderMapper.mapSort(orderBy.field()),
            orderBy == null ? null : GiteaOrderMapper.mapDirection(orderBy.direction()),
            page,
            first
        ),
        SearchResults::data
    );

    log.info(
        "Fetching repository data for organization: {}\nCurrent page: {}, Total repositories: {}",
        installation.getOrganizationId(),
        page,
        response.totalCount()
    );

    return repositoryMapper.map(after, response);
  }
}
