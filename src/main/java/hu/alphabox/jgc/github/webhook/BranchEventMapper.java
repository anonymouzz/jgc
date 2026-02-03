package hu.alphabox.jgc.github.webhook;

import hu.alphabox.jgc.TypeConversionMapper;
import hu.alphabox.jgc.config.MapperCentralConfig;
import hu.alphabox.jgc.gitea.webhook.GiteaWebhookBranchCreationEvent;
import hu.alphabox.jgc.gitea.webhook.GiteaWebhookBranchDeletionEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapperCentralConfig.class,
    uses = {
        TypeConversionMapper.class,
        InnerGitHubObjectMapper.class
    }
)
interface BranchEventMapper {

  @Mapping(target = "installation.id", source = "installationId")
  GitHubWebhookCreateEvent created(GiteaWebhookBranchCreationEvent event, Integer installationId);

  @Mapping(target = "installation.id", source = "installationId")
  GitHubWebhookDeleteEvent deleted(GiteaWebhookBranchDeletionEvent event, Integer installationId);
}
