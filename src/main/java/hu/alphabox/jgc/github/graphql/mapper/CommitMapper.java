package hu.alphabox.jgc.github.graphql.mapper;

import hu.alphabox.jgc.TypeConversionMapper;
import hu.alphabox.jgc.config.MapperCentralConfig;
import hu.alphabox.jgc.gitea.GiteaPageResponse;
import hu.alphabox.jgc.github.graphql.schema.Commit;
import hu.alphabox.jgc.github.graphql.schema.CommitEdge;
import hu.alphabox.jgc.github.graphql.schema.CommitHistoryConnection;
import hu.alphabox.jgc.github.graphql.schema.PageInfo;
import hu.alphabox.jgc.github.graphql.schema.Repository;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
    config = MapperCentralConfig.class,
    uses = {
        TypeConversionMapper.class,
        GitActorMapper.class
    }
)
public interface CommitMapper {

  @Mapping(target = "repository", expression = "java(repository)")
  @Mapping(target = "author", source = ".")
  @Mapping(target = "authoredDate", source = "created")
  @Mapping(target = "message", source = "commit.message")
  @Mapping(target = "oid", source = "sha")
  @Mapping(target = "url", source = "htmlUrl")
  @Mapping(target = "changedFiles", source = ".", qualifiedByName = "changedFiles")
  Commit node(hu.alphabox.jgc.gitea.client.model.Commit commit, @Context Repository repository);

  @Named("changedFiles")
  default Integer changedFiles(hu.alphabox.jgc.gitea.client.model.Commit commit) {
    return commit.files() != null ? commit.files().size() : null;
  }


  @Mapper(
      config = MapperCentralConfig.class,
      uses = {
          GitActorMapper.class,
          CommitMapper.class,
          TypeConversionMapper.class
      }
  )
  interface CommitHistoryMapper {

    default CommitHistoryConnection map(
        String cursorPage,
        GiteaPageResponse<hu.alphabox.jgc.gitea.client.model.Commit> giteaResponse,
        Repository repository
    ) {
      List<Commit> nodes = nodes(giteaResponse.data(), repository);
      PageInfo pageInfo = PageInfoMapper.map(cursorPage, giteaResponse);

      return new CommitHistoryConnection(
          nodes.stream().map(node -> edge(node, pageInfo.endCursor())).toList(),
          nodes,
          pageInfo,
          giteaResponse.totalCount()
      );
    }

    List<Commit> nodes(List<hu.alphabox.jgc.gitea.client.model.Commit> commits, @Context Repository repository);

    CommitEdge edge(Commit node, String cursor);
  }
}
