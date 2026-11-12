package hu.alphabox.jgc.github.graphql;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GraphQlLogFormatterTest {

  @Test
  void shapeKeepsPaginationAndDropsNodeContent() {
    Map<String, Object> node = new LinkedHashMap<>();
    node.put("title", "secret title");
    node.put("author", null);
    Map<String, Object> reviews = new LinkedHashMap<>();
    reviews.put("totalCount", 0);
    reviews.put("nodes", List.of());
    node.put("reviews", reviews);

    Map<String, Object> connection = new LinkedHashMap<>();
    connection.put("totalCount", 219);
    connection.put("pageInfo", orderedPageInfo());
    connection.put("edges", List.of(Map.of("cursor", "2", "node", node), Map.of("cursor", "2", "node", node)));
    connection.put("nodes", List.of(node, node));

    String shape = GraphQlLogFormatter.shape(Map.of("repository", Map.of("pullRequests", connection)));

    assertThat(shape)
        .contains("totalCount=219")
        .contains("hasNextPage=true")
        .contains("hasPreviousPage=false")
        .contains("endCursor=2")
        .contains("edges=[n=2, firstCursor=2, lastCursor=2]")
        .contains("nodes=[n=2, first={")
        .contains("author=null")
        .contains("reviews={totalCount=0, nodes=[n=0]}")
        .doesNotContain("secret title");
  }

  @Test
  void documentWithPullRequestsIsLoggedInFull() {
    String document = "query {\n  repository {\n    pullRequests(first: 100) { totalCount }\n  }\n}";

    assertThat(GraphQlLogFormatter.document(document))
        .isEqualTo("query { repository { pullRequests(first: 100) { totalCount } } }");
  }

  @Test
  void otherDocumentsAreLoggedAsShortLine() {
    String document = "query { viewer { login } }";

    assertThat(GraphQlLogFormatter.document(document))
        .startsWith("len=26 sha=")
        .endsWith("head=query { viewer { login } }");
  }

  @Test
  void cutMarksTruncatedText() {
    assertThat(GraphQlLogFormatter.cut("abcdef", 3)).isEqualTo("abc...[cut, total 6]");
    assertThat(GraphQlLogFormatter.cut("abc", 3)).isEqualTo("abc");
  }

  private static Map<String, Object> orderedPageInfo() {
    Map<String, Object> pageInfo = new LinkedHashMap<>();
    pageInfo.put("startCursor", "1");
    pageInfo.put("endCursor", "2");
    pageInfo.put("hasNextPage", true);
    pageInfo.put("hasPreviousPage", false);
    return pageInfo;
  }

}
