package hu.alphabox.jgc;

import hu.alphabox.jgc.config.MapperCentralConfig;
import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import org.mapstruct.Mapper;

@Mapper(config = MapperCentralConfig.class)
public interface TypeConversionMapper {

  default Instant mapToInstant(OffsetDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.toInstant();
  }

  default ZonedDateTime mapToZonedDateTime(OffsetDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.toZonedDateTime();
  }

  default String mapToString(OffsetDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.toString();
  }

  default OffsetDateTime mapToOffsetDateTime(ZonedDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.toOffsetDateTime();
  }

  default URI mapToURI(String url) {
    if (url == null) {
      return null;
    }
    return URI.create(url);
  }

  default String mapToString(Long number) {
    if (number == null) {
      return null;
    }
    return number.toString();
  }

  default Integer mapToInteger(Long number) {
    if (number == null) {
      return null;
    }
    return number.intValue();
  }
}
