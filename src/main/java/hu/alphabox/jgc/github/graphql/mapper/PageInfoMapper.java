
package hu.alphabox.jgc.github.graphql.mapper;

import hu.alphabox.jgc.LinkHeaderProcessor.LinkManager;
import hu.alphabox.jgc.gitea.GiteaPageResponse;
import hu.alphabox.jgc.github.graphql.schema.PageInfo;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageInfoMapper {

  static PageInfo map(String cursorPage, GiteaPageResponse<?> giteaResponse) {
    int page = Integer.parseInt(Objects.requireNonNullElse(cursorPage, "1"));
    LinkManager linkManager = giteaResponse.pageLinks();

    String startCursor = String.valueOf(page);
    String endCursor = linkManager.getNextPage().orElse(String.valueOf(page + 1));

    return new PageInfo(
        startCursor,
        endCursor,
        linkManager.hasPreviousPage(),
        linkManager.hasNextPage()
    );
  }
}
