package hu.alphabox.jgc.github.graphql.schema;

/**
 * The affiliation of a user to a repository
 */
public enum RepositoryAffiliation {

  /**
   * Repositories that the user has been added to as a collaborator.
   */
  COLLABORATOR,

  /**
   * Repositories that the user has access to through being a member of an organization. This includes every repository on every team that the user is on.
   */
  ORGANIZATION_MEMBER,

  /**
   * Repositories that are owned by the authenticated user.
   */
  OWNER
}
