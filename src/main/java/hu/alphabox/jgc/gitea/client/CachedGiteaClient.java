package hu.alphabox.jgc.gitea.client;

/**
 * Gitea client whose GET responses are cached for a short time.
 * Meant for the Atlassian backfill (GraphQL) only. Webhooks and the REST
 * handlers keep using the plain {@link GiteaClient}.
 */
public interface CachedGiteaClient extends GiteaClient {

}
