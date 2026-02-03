package hu.alphabox.jgc.github.rest.repository;

record GitHubRefResponse(
    String ref,
    String url,
    GitHubRefResponseObject object
) {

  record GitHubRefResponseObject(
      String sha,
      String type,
      String url
  ) {

  }

}
