package hu.alphabox.jgc.github.graphql.schema.scalar;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import java.util.Locale;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class VoidCoercing implements Coercing<Object, Object> {

  @Override
  public @Nullable Object serialize(@NonNull Object dataFetcherResult, @NonNull GraphQLContext graphQLContext, @NonNull Locale locale)
      throws CoercingSerializeException {
    return null;
  }

  @Override
  public @Nullable Object parseValue(@NonNull Object input, @NonNull GraphQLContext graphQLContext, @NonNull Locale locale) throws CoercingParseValueException {
    return null;
  }

  @Override
  public @Nullable Object parseLiteral(@NonNull Value<?> input, @NonNull CoercedVariables variables, @NonNull GraphQLContext graphQLContext,
      @NonNull Locale locale) throws CoercingParseLiteralException {
    return null;
  }
}
