# Upstream tracking

- **Upstream:** https://github.com/alphabox/jgc
- **This fork:** https://github.com/anonymouzz/jgc
- **Runtime image:** https://hub.docker.com/r/anonymouz/jgc

Pin the Ansible deploy to an image digest after the first successful Hub publish.

## Hub secrets (GitHub Actions)

Repository secrets:

- `DOCKERHUB_USERNAME` — e.g. `anonymouz`
- `DOCKERHUB_TOKEN` — Hub access token with push to `anonymouz/jgc`

Workflows push `anonymouz/jgc:latest` and `anonymouz/jgc:<sha12>` on `main`, and tag names on git tags.
