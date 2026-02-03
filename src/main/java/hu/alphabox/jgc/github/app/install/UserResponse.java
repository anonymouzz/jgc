package hu.alphabox.jgc.github.app.install;

record UserResponse(
    String login,
    Long id,
    String avatarUrl,
    ActorType type,
    String name
) {

}
