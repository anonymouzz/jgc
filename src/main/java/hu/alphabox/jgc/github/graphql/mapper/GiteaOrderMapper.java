package hu.alphabox.jgc.github.graphql.mapper;

import hu.alphabox.jgc.github.graphql.schema.OrderDirection;
import hu.alphabox.jgc.github.graphql.schema.RepositoryOrderField;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GiteaOrderMapper {

  public static String mapSort(RepositoryOrderField orderField) {
    return switch (orderField) {
      case CREATED_AT -> "created";
      case UPDATED_AT -> "updated";
      case STARGAZERS -> "stars";
      case NAME -> "alpha";
      case PUSHED_AT -> "updated";
    };
  }

  public static String mapDirection(OrderDirection orderDirection) {
    return switch (orderDirection) {
      case ASC -> "asc";
      case DESC -> "desc";
    };
  }

}
