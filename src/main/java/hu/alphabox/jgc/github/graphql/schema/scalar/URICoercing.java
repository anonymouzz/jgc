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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import org.jspecify.annotations.NonNull;

public class URICoercing implements Coercing<URI, String> {

  @Override
  public String serialize(@NonNull Object input, @NonNull GraphQLContext graphQLContext, @NonNull Locale locale)
      throws CoercingSerializeException {

    String result = String.valueOf(input);
    if (result == null) {
      throw new CoercingSerializeException(
          "Expected type 'URI' but was '" + typeName(input) + "'."
      );
    }
    return result;
  }

  @Override
  public URI parseValue(@NonNull Object input, @NonNull GraphQLContext graphQLContext, @NonNull Locale locale)
      throws CoercingParseValueException {
    if (input instanceof String s) {
      try {
        return new URI(s);
      } catch (URISyntaxException e) {
        throw new CoercingParseValueException(e);
      }
    }
    throw new CoercingParseValueException("Expected a value that can be converted to type 'URI' but it was a " + input.getClass().getName());
  }

  @Override
  public URI parseLiteral(@NonNull Value<?> input, @NonNull CoercedVariables variables, @NonNull GraphQLContext graphQLContext,
      @NonNull Locale locale) throws CoercingParseLiteralException {
    if (input instanceof StringValue value) {
      try {
        return new URI(value.getValue());
      } catch (URISyntaxException e) {
        throw new CoercingParseValueException(e);
      }
    }
    throw new CoercingParseLiteralException("Expected a value that can be converted to type 'URI' but it was a " + input.getClass().getName());
  }
}
