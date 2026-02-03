package hu.alphabox.jgc.github.graphql.schema;

import java.net.URI;
import java.time.Instant;

/**
 * A repository contains the content for a project.
 *
 * @param databaseId       Identifies the primary key from the database.
 * @param name             The name of the repository.
 * @param nameWithOwner    The repository's name with owner.
 * @param description      The description of the repository.
 * @param owner            The User owner of the repository.
 * @param url              The HTTP URL for this repository
 * @param createdAt        Identifies the date and time when the object was created.
 * @param updatedAt        Identifies the date and time when the object was last updated.
 * @param isPrivate        Identifies if the repository is private or internal.
 * @param defaultBranchRef The Ref associated with the repository's default branch.
 * @param deployments      Deployments associated with the repository
 * @param id               The Node ID of the Repository object
 */
public record Repository(
    int databaseId,
    String name,
    String nameWithOwner,
    String description,
    RepositoryOwner owner,
    URI url,
    Instant createdAt,
    Instant updatedAt,
    boolean isPrivate,
    Ref defaultBranchRef,
    DeploymentConnection deployments,
    String id
) {

}
