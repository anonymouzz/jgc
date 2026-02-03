package hu.alphabox.jgc.github.graphql;

import graphql.ExecutionInput;
import graphql.GraphQLError;
import graphql.ParseAndValidate;
import graphql.ParseAndValidateResult;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.graphql.support.ResourceDocumentSource;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class GraphQLQueryValidationTest {

  @Autowired
  private GraphQlSource source;

  @ParameterizedTest
  @ValueSource(strings = {
      "repositories",
      "repository-default-branch-history",
      "repository-deployments",
      "repository-pull-request-commits",
      "repository-pull-requests",
      "repository-refs-with-history"
  })
  void validate(String query) {
    ResourceDocumentSource documentSource = new ResourceDocumentSource(
        Collections.singletonList(new ClassPathResource("graphql-test/")),
        ResourceDocumentSource.FILE_EXTENSIONS
    );

    ParseAndValidateResult result = ParseAndValidate.parseAndValidate(
        source.schema(),
        ExecutionInput.newExecutionInput(documentSource.getDocument(query).block())
            .variables(Map.of("per_page", 1))
            .build()
    );

    Assertions.assertThat(result.isFailure())
        .withFailMessage(() -> result.getErrors().stream().map(GraphQLError::getMessage).collect(Collectors.joining(System.lineSeparator())))
        .isFalse();
  }
}
