package hu.alphabox.jgc.github.graphql.mapper;

import hu.alphabox.jgc.TypeConversionMapper;
import hu.alphabox.jgc.config.MapperCentralConfig;
import hu.alphabox.jgc.gitea.client.GiteaClient;
import hu.alphabox.jgc.gitea.client.model.Commit;
import hu.alphabox.jgc.github.graphql.schema.Actor;
import hu.alphabox.jgc.github.graphql.schema.GitActor;
import hu.alphabox.jgc.github.graphql.schema.Organization;
import hu.alphabox.jgc.github.graphql.schema.User;
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
public abstract class GitActorMapper {

  @Setter(onMethod = @__(@Autowired))
  private GiteaClient giteaClient;

  @Named("actor")
  public Actor actor(hu.alphabox.jgc.gitea.client.model.User user) {
    try {
      giteaClient.orgGet(user.login());
      return organization(user);
    } catch (HttpClientErrorException _) {
      return user(user);
    }
  }

  @Named("organization")
  @Mapping(target = "url", source = "htmlUrl")
  public abstract Organization organization(hu.alphabox.jgc.gitea.client.model.User user);

  @Named("user")
  @Mapping(target = "name", source = "fullName")
  @Mapping(target = "url", source = "htmlUrl")
  @Mapping(target = "databaseId", source = "id")
  public abstract User user(hu.alphabox.jgc.gitea.client.model.User user);

  @Mapping(target = "user", source = ".", qualifiedByName = "user")
  public abstract GitActor gitActor(hu.alphabox.jgc.gitea.client.model.User user);

  @Mapping(
      target = "avatarUrl",
      source = "author.avatarUrl",
      defaultValue = "https://raw.githubusercontent.com/detain/svg-logos/b02ee1ac30c7ff4757278337c95588b01ed0954b/svg/g/gitea.svg"
  )
  @Mapping(target = "email", source = "commit.author.email")
  @Mapping(target = "name", source = "commit.author.name")
  @Mapping(target = "user", source = "author", qualifiedByName = "user")
  public abstract GitActor gitActor(Commit commit);
}
