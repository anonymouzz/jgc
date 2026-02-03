package hu.alphabox.jgc.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogTopic {

  public static final String BACKFILL_DATA_FETCHING = "hu.alphabox.backfill.data.fetching";
  public static final String REQUEST_FAILURE = "hu.alphabox.request.failure";
}
