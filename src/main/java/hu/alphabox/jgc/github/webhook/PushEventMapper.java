package hu.alphabox.jgc.github.webhook;

import hu.alphabox.jgc.TypeConversionMapper;
import hu.alphabox.jgc.config.MapperCentralConfig;
import hu.alphabox.jgc.gitea.webhook.GiteaWebhookPushEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapperCentralConfig.class,
    uses = {
        TypeConversionMapper.class,
        InnerGitHubObjectMapper.class
    }
)
interface PushEventMapper {

  @Mapping(target = "installation.id", source = "installationId")
  GitHubWebhookPushEvent map(GiteaWebhookPushEvent event, Integer installationId);
}
