# Gitea to Jira GitHub Connector

[![Java Version](https://img.shields.io/badge/Java-25-blue.svg)](https://openjdk.java.net/)
[![Framework](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

A middleware application that acts as a bridge between Gitea and the **GitHub for Atlassian Plugin**.

## Overview

The GitHub for Atlassian Plugin communicates directly with the GitHub API. Gitea, while similar, has its own API and webhook structure.
This application solves that problem by acting as a translation layer.

It works in two main ways:

1. **Webhook Translation (Gitea → Jira):** It consumes webhooks from your Gitea repositories, transforms the
   payloads into the format GitHub would send, and forwards them to your GitHub for Atlassian Plugin's webhook endpoint.
2. **API Proxy (Jira → Gitea):** It exposes a minimal, GitHub-compatible REST API / GraphQL interface. When the Jira plugin makes requests (e.g., to fetch
   specific commit
   data), this app intercepts them, translates them into Gitea API calls, and returns the data in the GitHub format Jira expects.

## Configuration

### Gitea

To use our middleware, you have to create a token (Access Token), accessed via User Settings → Applications. You can create a Bot Account for this, or use an
existing one. For the token, you have to add the following permissions:

* `write:organization`: Create the necessary webhook
* `write:issue`: In a PR, when you put an issue key inside square brackets, it will be converted to a link
* `write:repository`: Create new branches from Jira
* `read:user`: Read the user details and organization's data that related to it

_Note:_ This account needs to have an admin access to those organizations that you want to connect to Jira.

### Application

This application is configured using Spring Boot's externalized configuration, typically via `application.properties` or `application.yml`.
You can specify these as command line arguments or environment variables too.

The following parameters are required to run the application:

| Environment variable                   | Property Key                            | Description                                                     |
|----------------------------------------|:----------------------------------------|:----------------------------------------------------------------|
| `APPLICATION_BASEURL`                  | `application.base-url`                  | The external URL that the GitHub plugin calls                   |
| `APPLICATION_INSTALLATIONPATH`         | `application.insllatation-path`         | The path where the app can store the installations' information |
| `APPLICATION_GITEA_BASEURL`            | `application.gitea.base-url`            | The base URL of your Gitea instance.                            |
| `APPLICATION_GITEA_CLIENT_ACCESSTOKEN` | `application.gitea.client.access-token` | An API token from Gitea.                                        |

#### Gitea concurrency limit

To throttle outgoing traffic to your Gitea instance, you can set a concurrency limit.

| Environment variable                        | Property Key                                 | Default value |
|---------------------------------------------|:---------------------------------------------|:--------------|
| `APPLICATION_GITEA_CLIENT_CONCURRENCYLIMIT` | `application.gitea.client.concurrency-limit` | `12`          | 

Increasing this value may improve performance for bulk operations but could lead to errors or high CPU usage on the Gitea server if set too high.

_Note:_ At backfill, you could see some `Unresolved RestClientException` that caused by concurrency and GraphQL timeout limits. Don't worry about it, the Jira
plugin will retry these GraphQL requests.

### Jira setup

* Install [GitHub for Atlassian](https://marketplace.atlassian.com/apps/1219592/github-for-atlassian) plugin
* At configure, select "Connect GitHub Server"
* As a Server URL, add the value of `application.base-url`
* Select automatic app creation and follow the instructions

## Building

From the root of the source tree, run:

```shell
./mvnw verify
```

It will generate a runnable JAR under `target` directory, namely: `jira-gitea-connector.jar`

If you want to create an OCI image, you can do that using CNB (Cloud Native Buildpacks).
By default, it creates an image with name `alphabox/jira-gitea-connector:latest`, but you can configure it via `image.name` system property.

```shell
# Image build
./mvnw spring-boot:build-image
# Image build with custom name
./mvnw -Dimage.name=jgc:latest spring-boot:build-image
```

You can add additional tags with `image.additional-names` system property. The only downside is you have to add the tags as full image references separated by
comma.
For more information, you can check the [Spring Boot doc](https://docs.spring.io/spring-boot/maven-plugin/build-image.html#build-image.customization.tags).

## Using

```shell
# Using JAR
java -jar target/jira-gitea-connector.jar --spring.config.location=application.yaml
# Using OCI image with command line parameters
docker run --rm alphabox/jira-gitea-connector:latest --application.base-url= ...
# Using OCI image with attached application.yaml
docker run --rm -v ./application.yaml:/tmp/application.yaml alphabox/jira-gitea-connector:latest --spring.config.location=/tmp/application.yaml
```

## Contributing

### Code Style and Conventions

* [Google Style Guide](https://google.github.io/styleguide/javaguide.html): All code must conform to the Google Java Style Guide. In this project, the column
  limit is increased to 160 characters.
* Java 25: Adhere to modern Java conventions.
* Records: New data classes should be implemented as Java Records.


