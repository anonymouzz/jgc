package hu.alphabox.jgc.github.rest.repository;

import hu.alphabox.jgc.TypeConversionMapper;
import hu.alphabox.jgc.config.MapperCentralConfig;
import hu.alphabox.jgc.gitea.client.model.Branch;
import hu.alphabox.jgc.gitea.client.model.Commit;
import hu.alphabox.jgc.gitea.client.model.CommitAffectedFiles;
import hu.alphabox.jgc.gitea.client.model.Reference;
import hu.alphabox.jgc.gitea.client.model.User;
import hu.alphabox.jgc.github.rest.repository.GitHubCommitResponse.DiffEntry;
import java.net.URI;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Mapper(
    config = MapperCentralConfig.class,
    uses = TypeConversionMapper.class
)
interface GitHubRefMapper {

  List<GitHubBranchResponse> branches(List<Branch> branch);

  @Mapping(target = "commit.sha", source = "commit.id")
  GitHubBranchResponse branch(Branch branch);

  @Mapping(target = "ref", source = "ref")
  @Mapping(target = "url", source = "branch.commit.url")
  GitHubRefResponse refFromBranch(String ref, Branch branch);

  GitHubRefResponse ref(Reference reference);

  @Mapping(target = "files", source = ".")
  GitHubCommitResponse commit(Commit commit);

  @Mapping(target = "name", source = "fullName")
  GitHubCommitResponse.User commitAuthor(User user);

  @ValueMapping(target = "ADDED", source = "added")
  @ValueMapping(target = "MODIFIED", source = "modified")
  @ValueMapping(target = "REMOVED", source = "deleted")
  @ValueMapping(target = "REMOVED", source = "removed")
  @ValueMapping(target = MappingConstants.THROW_EXCEPTION, source = MappingConstants.ANY_UNMAPPED)
  DiffEntry.Status diffEntryStatus(String status);

  default List<DiffEntry> diffEntryList(Commit commit) {
    URI baseCommitUrl = URI.create(commit.htmlUrl());
    String[] pathSegments = baseCommitUrl.getPath().split("/");

    String organization = pathSegments[1];
    String repository = pathSegments[2];
    String commitHash = pathSegments[4];

    UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
        .scheme(baseCommitUrl.getScheme())
        .host(baseCommitUrl.getHost())
        .pathSegment(
            organization,
            repository,
            "blob",
            commitHash
        );

    return commit.files().stream()
        .map(file ->
            diffEntry(
                file,
                builder
                    .cloneBuilder()
                    .pathSegment(file.filename())
                    .build()
                    .toUri()
            )
        )
        .toList();
  }

  DiffEntry diffEntry(CommitAffectedFiles commitAffectedFiles, URI blobUrl);
}
