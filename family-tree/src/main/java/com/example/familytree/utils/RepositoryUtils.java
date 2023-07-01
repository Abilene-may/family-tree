package com.example.familytree.utils;

import jakarta.persistence.Query;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections4.MapUtils;

public class RepositoryUtils {
  public static void setQueryParameters(Map<String, Object> queryParams, Query query) {
    if (MapUtils.isNotEmpty(queryParams)) {
      queryParams.forEach(query::setParameter);
    }
  }

  public static <T> T setValueForField(Class<T> c, Object object) {
    if (object == null) {
      return null;
    } else if (StringUtils.equals(Long.class.getName(), c.getName())) {
      object = ((BigInteger) object).longValue();
    } else if (StringUtils.equals(LocalDate.class.getName(), c.getName())) {
      object = ((Date) object).toLocalDate();
    } else if (StringUtils.equals(LocalDateTime.class.getName(), c.getName())) {
      object = ((Timestamp) object).toLocalDateTime();
    }
    return (T) object;
  }
}
