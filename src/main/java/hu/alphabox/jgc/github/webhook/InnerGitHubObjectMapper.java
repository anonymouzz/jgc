package hu.alphabox.jgc.github.webhook;

import hu.alphabox.jgc.TypeConversionMapper;
import hu.alphabox.jgc.config.MapperCentralConfig;
import hu.alphabox.jgc.gitea.client.model.User;
import hu.alphabox.jgc.gitea.webhook.GiteaWebhookUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapperCentralConfig.class,
    uses = TypeConversionMapper.class
)
interface InnerGitHubObjectMapper {

  @Mapping(target = "name", source = "username")
  GitHubWebhookUser map(GiteaWebhookUser owner);

  @Mapping(target = "name", source = "login")
  GitHubWebhookUser map(User user);
}
