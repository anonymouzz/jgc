package hu.alphabox.jgc.github.graphql.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import hu.alphabox.jgc.LinkHeaderProcessor;
import hu.alphabox.jgc.gitea.GiteaPageResponse;
import hu.alphabox.jgc.github.graphql.schema.PageInfo;
import java.util.List;
import org.junit.jupiter.api.Test;

class PageInfoMapperTest {

  private static final String BASE = "https://gitea.example/api/v1/repos/o/r/pulls?state=all&limit=50";

  private static GiteaPageResponse<String> response(String... relations) {
    List<String> links = List.of(String.join(", ", List.of(relations)));
    return new GiteaPageResponse<>(List.of("x"), LinkHeaderProcessor.parse(links), 219);
  }

  private static String link(int page, String rel) {
    return "<%s&page=%d>; rel=\"%s\"".formatted(BASE, page, rel);
  }

  @Test
  void firstPageHasNextAndNoPrevious() {
    PageInfo info = PageInfoMapper.map("1", response(link(2, "next"), link(5, "last")));

    assertThat(info.hasNextPage()).isTrue();
    assertThat(info.hasPreviousPage()).isFalse();
    assertThat(info.startCursor()).isEqualTo("1");
    assertThat(info.endCursor()).isEqualTo("2");
  }

  @Test
  void middlePageHasBothDirections() {
    PageInfo info = PageInfoMapper.map(
        "3",
        response(link(4, "next"), link(5, "last"), link(1, "first"), link(2, "prev"))
    );

    assertThat(info.hasNextPage()).isTrue();
    assertThat(info.hasPreviousPage()).isTrue();
    assertThat(info.endCursor()).isEqualTo("4");
  }

  @Test
  void lastPageHasPreviousAndNoNext() {
    PageInfo info = PageInfoMapper.map("5", response(link(1, "first"), link(4, "prev")));

    assertThat(info.hasNextPage()).isFalse();
    assertThat(info.hasPreviousPage()).isTrue();
  }

  @Test
  void singlePageWithoutLinkHeaderHasNoDirections() {
    PageInfo info = PageInfoMapper.map(null, GiteaPageResponse.empty());

    assertThat(info.hasNextPage()).isFalse();
    assertThat(info.hasPreviousPage()).isFalse();
    assertThat(info.startCursor()).isEqualTo("1");
  }

}
