package hu.alphabox.jgc.github.graphql.schema;

/**
 * The possible states for a deployment status.
 */
public enum DeploymentStatusState {
  /**
   * The deployment experienced an error.
   */
  ERROR,
  /**
   * The deployment has failed.
   */
  FAILURE,
  /**
   * The deployment is inactive.
   */
  INACTIVE,
  /**
   * The deployment is in progress.
   */
  IN_PROGRESS,
  /**
   * The deployment is pending.
   */
  PENDING,
  /**
   * The deployment is queued
   */
  QUEUED,
  /**
   * The deployment was successful.
   */
  SUCCESS,
  /**
   * The deployment is waiting.
   */
  WAITING
}
