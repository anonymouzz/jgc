package hu.alphabox.jgc.github.graphql;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Builds short log text for GraphQL requests and responses.
 * The response shape keeps only structure and pagination fields. It never
 * prints node content, so author names, titles and similar data stay out.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class GraphQlLogFormatter {

  private static final int MAX_DEPTH = 8;
  private static final int MAX_SHAPE_LENGTH = 3000;
  private static final int MAX_DOCUMENT_LENGTH = 8000;
  private static final int HEAD_LENGTH = 100;
  private static final String FULL_DOCUMENT_MARKER = "pullRequests";
  private static final Set<String> SCALAR_KEYS = Set.of(
      "totalCount",
      "hasNextPage",
      "hasPreviousPage",
      "startCursor",
      "endCursor"
  );

  /**
   * Full text for documents that select pull requests. One short line for
   * all other documents: length, hash and the first characters.
   */
  static String document(String document) {
    String compact = document == null ? "" : document.replaceAll("\\s+", " ").trim();
    if (compact.contains(FULL_DOCUMENT_MARKER)) {
      return cut(compact, MAX_DOCUMENT_LENGTH);
    }
    return "len=%d sha=%s head=%s".formatted(compact.length(), shortHash(compact), cut(compact, HEAD_LENGTH));
  }

  /**
   * Structure of the response data: map keys, list sizes, pagination fields,
   * and every null value. Lists show the size and the first element only.
   */
  static String shape(Object data) {
    StringBuilder out = new StringBuilder();
    append(out, data, 0);
    return cut(out.toString(), MAX_SHAPE_LENGTH);
  }

  static String cut(String text, int maxLength) {
    if (text == null || text.length() <= maxLength) {
      return text;
    }
    return text.substring(0, maxLength) + "...[cut, total " + text.length() + "]";
  }

  private static void append(StringBuilder out, Object value, int depth) {
    if (value == null) {
      out.append("null");
      return;
    }
    if (depth > MAX_DEPTH) {
      out.append("...");
      return;
    }
    if (value instanceof Map<?, ?> map) {
      appendMap(out, map, depth);
    } else if (value instanceof List<?> list) {
      out.append("[n=").append(list.size());
      if (!list.isEmpty()) {
        out.append(", first=");
        append(out, list.getFirst(), depth + 1);
      }
      out.append(']');
    } else {
      out.append(value);
    }
  }

  private static void appendMap(StringBuilder out, Map<?, ?> map, int depth) {
    out.append('{');
    boolean first = true;
    for (Map.Entry<?, ?> entry : map.entrySet()) {
      String key = String.valueOf(entry.getKey());
      Object value = entry.getValue();
      boolean structural = value == null || value instanceof Map || value instanceof List;
      if (!structural && !SCALAR_KEYS.contains(key)) {
        continue;
      }
      if (!first) {
        out.append(", ");
      }
      first = false;
      out.append(key).append('=');
      if ("edges".equals(key) && value instanceof List<?> edges) {
        appendEdges(out, edges);
      } else {
        append(out, value, depth + 1);
      }
    }
    out.append('}');
  }

  private static void appendEdges(StringBuilder out, List<?> edges) {
    out.append("[n=").append(edges.size());
    if (!edges.isEmpty()) {
      out.append(", firstCursor=").append(cursor(edges.getFirst()));
      out.append(", lastCursor=").append(cursor(edges.getLast()));
    }
    out.append(']');
  }

  private static Object cursor(Object edge) {
    return edge instanceof Map<?, ?> map ? map.get("cursor") : null;
  }

  private static String shortHash(String text) {
    try {
      byte[] digest = MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(digest, 0, 6);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 is not available", e);
    }
  }

}
