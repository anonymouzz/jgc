package hu.alphabox.jgc.github;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.zip.CRC32;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class GitHubStyleTokenGenerator {

  private static final SecureRandom random = new SecureRandom();
  private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final int RANDOM_PART_LENGTH = 30;
  private static final int CHECKSUM_LENGTH = 6;

  static String generateToken(String prefix) {
    StringBuilder sb = new StringBuilder(prefix).append("_");

    String randomPart = randomBase62();
    sb.append(randomPart);

    String checksum = crc32Base62(prefix + "_" + randomPart);
    sb.append(checksum);

    return sb.toString();
  }

  private static String randomBase62() {
    StringBuilder sb = new StringBuilder(RANDOM_PART_LENGTH);
    for (int i = 0; i < RANDOM_PART_LENGTH; i++) {
      sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
    }
    return sb.toString();
  }

  private static String crc32Base62(String input) {
    CRC32 crc = new CRC32();
    crc.update(input.getBytes(StandardCharsets.UTF_8));
    long crcValue = crc.getValue();

    StringBuilder sb = new StringBuilder();
    while (crcValue > 0) {
      int rem = (int) (crcValue % 62);
      sb.append(BASE62.charAt(rem));
      crcValue /= 62;
    }
    sb.reverse();

    while (sb.length() < CHECKSUM_LENGTH) {
      sb.insert(0, '0');
    }
    if (sb.length() > CHECKSUM_LENGTH) {
      sb = new StringBuilder(sb.substring(sb.length() - CHECKSUM_LENGTH));
    }

    return sb.toString();
  }
}
