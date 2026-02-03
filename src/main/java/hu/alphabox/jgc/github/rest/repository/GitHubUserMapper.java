package hu.alphabox.jgc.github.rest.repository;

import hu.alphabox.jgc.TypeConversionMapper;
import hu.alphabox.jgc.config.MapperCentralConfig;
import hu.alphabox.jgc.gitea.client.GiteaClient;
import hu.alphabox.jgc.gitea.client.model.User;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

@Mapper(
    config = MapperCentralConfig.class,
    uses = TypeConversionMapper.class
)
abstract class GitHubUserMapper {

  @Setter(onMethod_ = @Autowired)
  private GiteaClient giteaClient;

  @Mapping(target = "name", source = "fullName")
  @Mapping(target = "siteAdmin", source = "isAdmin")
  @Mapping(target = "createdAt", source = "created")
  @Mapping(target = "type", source = "login", qualifiedByName = "type")
  abstract GitHubUserResponse map(User user);

  @Named("type")
  protected String type(String login) {
    try {
      giteaClient.orgGet(login);
      return "Organization";
    } catch (HttpClientErrorException _) {
      return "User";
    }
  }

}
