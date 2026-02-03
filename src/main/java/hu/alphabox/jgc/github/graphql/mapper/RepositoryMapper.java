package hu.alphabox.jgc.github.graphql.mapper;

import hu.alphabox.jgc.TypeConversionMapper;
import hu.alphabox.jgc.config.MapperCentralConfig;
import hu.alphabox.jgc.gitea.GiteaPageResponse;
import hu.alphabox.jgc.github.graphql.schema.PageInfo;
import hu.alphabox.jgc.github.graphql.schema.Ref;
import hu.alphabox.jgc.github.graphql.schema.Repository;
import hu.alphabox.jgc.github.graphql.schema.RepositoryConnection;
import hu.alphabox.jgc.github.graphql.schema.RepositoryEdge;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    config = MapperCentralConfig.class,
    uses = {
        TypeConversionMapper.class,
        GitActorMapper.class
    }
)
public interface RepositoryMapper {

  default RepositoryConnection map(
      String cursorPage,
      GiteaPageResponse<hu.alphabox.jgc.gitea.client.model.Repository> giteaResponse
  ) {
    List<Repository> nodes = nodes(giteaResponse.data());
    PageInfo pageInfo = PageInfoMapper.map(cursorPage, giteaResponse);

    return new RepositoryConnection(
        nodes.stream().map(node -> edge(node, pageInfo.endCursor())).toList(),
        nodes,
        pageInfo,
        giteaResponse.totalCount()
    );
  }

  List<Repository> nodes(List<hu.alphabox.jgc.gitea.client.model.Repository> repository);

  @Mapping(target = "databaseId", source = "id")
  @Mapping(target = "url", source = "htmlUrl")
  @Mapping(target = "nameWithOwner", source = "fullName")
  @Mapping(target = "isPrivate", source = "_private")
  @Mapping(target = "defaultBranchRef", source = "defaultBranch")
  @Mapping(target = "owner", source = "owner", qualifiedByName = "user")
  Repository node(hu.alphabox.jgc.gitea.client.model.Repository repository);

  RepositoryEdge edge(Repository node, String cursor);

  @Mapping(target = "name", source = "defaultBranch")
  @Mapping(target = "prefix", constant = "refs/heads/")
  Ref ref(String defaultBranch);

  @AfterMapping
  default void linkRepository(@MappingTarget Repository target) {
    if (target.defaultBranchRef() != null) {
      target.defaultBranchRef().setRepository(target);
    }
  }
}
