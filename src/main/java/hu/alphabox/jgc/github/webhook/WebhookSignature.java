package hu.alphabox.jgc.github.webhook;

import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebhookSignature {

  public static class HashingException extends RuntimeException {

    HashingException(String message, Throwable cause) {
      super(message, cause);
    }
  }

  public static String generateSignature(byte[] content, String secret) {
    return generate("HmacSHA256", content, secret);
  }

  public static String generateLegacySignature(byte[] content, String secret) {
    return generate("HmacSHA1", content, secret);
  }

  private static String generate(String algorithm, byte[] content, String secret) {
    try {
      SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), algorithm);
      Mac mac = Mac.getInstance(algorithm);
      mac.init(secretKey);

      byte[] hmacBytes = mac.doFinal(content);
      StringBuilder sb = new StringBuilder();
      for (byte b : hmacBytes) {
        sb.append("%02x".formatted(b));
      }

      return sb.toString();
    } catch (Exception e) {
      throw new HashingException("Unable to create signature for webhook event!", e);
    }
  }


}
