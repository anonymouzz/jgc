package hu.alphabox.jgc;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LinkHeaderProcessor {

  private static final Pattern LINK_PATTERN = Pattern.compile("<([^>]+)>;\\s*rel=\"([^\"]+)\"");

  public static LinkManager parse(List<String> linkHeaders) {
    if (CollectionUtils.isEmpty(linkHeaders)) {
      return LinkManager.empty();
    }

    return new LinkManager(
        linkHeaders.stream()
            .map(LINK_PATTERN::matcher)
            .flatMap(Matcher::results)
            .map(match -> new Link(match.group(1), match.group(2)))
            .toList()
    );
  }

  public static LinkManager create(
      URI baseURI,
      int limit,
      int page,
      int totalCount
  ) {
    List<Link> links = new ArrayList<>();
    int lastPage = (int) Math.ceil((double) totalCount / limit);

    String hrefFormat = "%s?per_page=%d&page=%d";
    if (page > 1) {
      links.add(new Link(hrefFormat.formatted(baseURI, limit, page - 1), "prev"));
    }
    if (page != lastPage) {
      links.add(new Link(hrefFormat.formatted(baseURI, limit, page + 1), "next"));
      links.add(new Link(hrefFormat.formatted(baseURI, limit, lastPage), "last"));
    }

    if (page != 1) {
      links.add(new Link("%s?per_page=%d&page=1".formatted(baseURI, limit), "first"));
    }
    return new LinkManager(links);
  }

  @RequiredArgsConstructor(access = AccessLevel.PUBLIC)
  public static class LinkManager {

    private final List<Link> links;

    public static LinkManager empty() {
      return new LinkManager(Collections.emptyList());
    }

    public String toHeader() {
      return links.stream()
          .map(Link::toString)
          .collect(Collectors.joining(", "));
    }

    public Optional<String> getNextPage() {
      return links.stream()
          .filter(link -> "next".equals(link.relation))
          .map(link -> UriComponentsBuilder
              .fromUriString(link.href)
              .build()
              .getQueryParams()
              .getFirst("page"))
          .filter(Objects::nonNull)
          .findFirst();
    }

    public boolean hasNextPage() {
      return links.stream()
          .map(Link::relation)
          .anyMatch(rel -> rel.equals("next"));
    }

    public boolean hasPreviousPage() {
      return links.stream()
          .map(Link::relation)
          .anyMatch(rel -> rel.equals("prev"));
    }

  }

  private record Link(
      String href,
      String relation
  ) {


    @Override
    public String toString() {
      return "<%s>; rel=\"%s\"".formatted(href, relation);
    }
  }
}
