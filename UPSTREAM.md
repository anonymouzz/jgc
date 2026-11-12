# Upstream tracking

- **Upstream:** https://github.com/alphabox/jgc
- **This fork:** https://github.com/anonymouzz/jgc
- **Runtime image:** https://hub.docker.com/r/anonymouz/jgc

Pin the Ansible deploy to an image digest after the first successful Hub publish.

## Fork deltas (vs alphabox)

- GraphQL HTTP timeout is configurable: `APPLICATION_GRAPHQL_TIMEOUTSECONDS` / `application.graphql.timeout-seconds` (default `10`). Needed so Atlassian org backfill can finish large Gitea repos without jgc aborting at 10s.
- Gitea GET response cache for the backfill path (`CachedGiteaClient`, used by the GraphQL controllers only). In-process Caffeine, key = full request URI, 2xx only, concurrent identical requests collapse into one Gitea call. No explicit invalidation: list pages can be up to TTL stale. `APPLICATION_GITEA_CLIENT_CACHETTL` (default `10m`), `APPLICATION_GITEA_CLIENT_CACHEMAXSIZE` (default `32MB`). Metrics: `cache_*{cache="gitea_response"}` on `/actuator/prometheus`.
- Gitea concurrency limit moved from `@ConcurrencyLimit` on the interface to a `RestClient` interceptor shared by both clients. Cache hits do not take a permit. Same env: `APPLICATION_GITEA_CLIENT_CONCURRENCYLIMIT`.

## Hub secrets (GitHub Actions)

Repository secrets:

- `DOCKERHUB_USERNAME` — e.g. `anonymouz`
- `DOCKERHUB_TOKEN` — Hub access token with push to `anonymouz/jgc`

Workflows push `anonymouz/jgc:latest` and `anonymouz/jgc:<sha12>` on `main`, and tag names on git tags.
