package hu.alphabox.jgc.github.graphql.schema;

import lombok.Data;

/**
 * A request for a user to review a pull request.
 */
@Data
public class ReviewRequest {

  /*
   * The reviewer that is requested.
   */
  private final RequestedReviewer requestedReviewer;

}
