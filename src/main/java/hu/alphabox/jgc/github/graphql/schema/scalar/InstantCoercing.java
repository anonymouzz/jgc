package hu.alphabox.jgc.github.graphql.schema.scalar;

import static graphql.scalar.CoercingUtil.typeName;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import java.time.Instant;
import java.util.Locale;
import org.jspecify.annotations.NonNull;

class InstantCoercing implements Coercing<Instant, String> {

  @Override
  public String serialize(@NonNull Object input, @NonNull GraphQLContext graphQLContext, @NonNull Locale locale)
      throws CoercingSerializeException {

    String result = String.valueOf(input);
    if (result == null) {
      throw new CoercingSerializeException(
          "Expected type 'Instant' but was '" + typeName(input) + "'."
      );
    }
    return result;
  }

  @Override
  public Instant parseValue(@NonNull Object input, @NonNull GraphQLContext graphQLContext, @NonNull Locale locale)
      throws CoercingParseValueException {
    if (input instanceof String s) {
      return Instant.parse(s);
    }
    throw new CoercingParseValueException("Expected a value that can be converted to type 'Instant' but it was a " + input.getClass().getName());
  }

  @Override
  public Instant parseLiteral(@NonNull Value<?> input, @NonNull CoercedVariables variables, @NonNull GraphQLContext graphQLContext,
      @NonNull Locale locale) throws CoercingParseLiteralException {
    if (input instanceof StringValue value) {
      return Instant.parse(value.getValue());
    }
    throw new CoercingParseLiteralException("Expected a value that can be converted to type 'Instant' but it was a " + input.getClass().getName());
  }
}
