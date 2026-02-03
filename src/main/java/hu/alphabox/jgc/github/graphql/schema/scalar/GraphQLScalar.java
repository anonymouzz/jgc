package hu.alphabox.jgc.github.graphql.schema.scalar;

import graphql.scalar.GraphqlStringCoercing;
import graphql.schema.GraphQLScalarType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GraphQLScalar {

  public static final GraphQLScalarType DATE_TIME = GraphQLScalarType.newScalar()
      .name("DateTime")
      .coercing(new InstantCoercing())
      .build();

  public static final GraphQLScalarType GIT_TIMESTAMP = GraphQLScalarType.newScalar()
      .name("GitTimestamp")
      .coercing(new InstantCoercing())
      .build();

  public static final GraphQLScalarType GIT_OBJECT_ID = GraphQLScalarType.newScalar()
      .name("GitObjectID")
      .coercing(new GraphqlStringCoercing())
      .build();

  public static final GraphQLScalarType URI = GraphQLScalarType.newScalar()
      .name("URI")
      .coercing(new URICoercing())
      .build();

  public static final GraphQLScalarType VOID = GraphQLScalarType.newScalar()
      .name("Void")
      .coercing(new VoidCoercing())
      .build();

}
