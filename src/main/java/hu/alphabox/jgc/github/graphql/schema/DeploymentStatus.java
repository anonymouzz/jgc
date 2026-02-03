package hu.alphabox.jgc.github.graphql.schema;

import java.net.URI;
import java.time.Instant;

/**
 * Describes the status of a given deployment attempt.
 *
 * @param createdAt      Identifies the date and time when the object was created.
 * @param updatedAt      Identifies the date and time when the object was last updated.
 * @param state          Identifies the current state of the deployment.
 * @param logoUrl        Identifies the log URL of the deployment.
 * @param environmentUrl Identifies the environment URL of the deployment.
 * @param id             The Node ID of the DeploymentStatus object
 */
public record DeploymentStatus(
    Instant createdAt,
    Instant updatedAt,
    DeploymentStatusState state,
    URI logoUrl,
    URI environmentUrl,
    String id
) {

}
