package hu.alphabox.jgc.github.graphql.schema;

/**
 * Represents a Git tag.
 *
 * @param oid The Git object ID
 */
public record Tag(String oid) implements GitObject {

}
