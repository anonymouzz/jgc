package hu.alphabox.jgc.gitea;

import hu.alphabox.jgc.LinkHeaderProcessor;
import hu.alphabox.jgc.LinkHeaderProcessor.LinkManager;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public record GiteaPageResponse<T>(
    List<T> data,
    LinkManager pageLinks,
    int totalCount
) {

  public static <T> GiteaPageResponse<T> empty() {
    return new GiteaPageResponse<>(
        Collections.emptyList(),
        LinkManager.empty(),
        0
    );
  }

  public static <T> GiteaPageResponse<T> of(ResponseEntity<List<T>> response) {
    return of(response, Function.identity());
  }

  public static <P, D> GiteaPageResponse<D> of(ResponseEntity<P> response, Function<P, List<D>> mapper) {
    if (response.hasBody()) {
      HttpHeaders headers = response.getHeaders();
      return new GiteaPageResponse<>(
          mapper.apply(response.getBody()),
          LinkHeaderProcessor.parse(headers.get(HttpHeaders.LINK)),
          Integer.parseInt(Objects.requireNonNullElse(headers.getFirst("X-Total-Count"), "0"))
      );
    }
    return empty();
  }

}
