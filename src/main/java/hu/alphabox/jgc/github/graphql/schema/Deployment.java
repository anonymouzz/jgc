package hu.alphabox.jgc.github.graphql.schema;

import java.time.Instant;

/**
 * Represents triggered deployment instance.
 *
 * @param createdAt    Identifies the date and time when the object was created.
 * @param repository   Identifies the repository associated with the deployment.
 * @param databaseId   Identifies the primary key from the database.
 * @param commitOid    Identifies the oid of the deployment commit, even if the commit has been deleted.
 * @param task         The deployment task.
 * @param ref          Identifies the Ref of the deployment, if the deployment was created by ref.
 * @param environment  The latest environment to which this deployment was made.
 * @param description  The deployment description.
 * @param statuses     A list of statuses associated with the deployment.
 * @param latestStatus The latest status of this deployment.
 */
public record Deployment(
    Instant createdAt,
    Repository repository,
    Integer databaseId,
    String commitOid,
    String task,
    Ref ref,
    String environment,
    String description,
    DeploymentStatusConnection statuses,
    DeploymentStatus latestStatus
) {

}
