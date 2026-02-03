package hu.alphabox.jgc.gitea.client.model;

import java.util.List;

public record SearchResults(
    List<Repository> data
) {

}

