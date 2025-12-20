package com.javanc.ultis;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateToLocalDateTimeUltis {
    public static LocalDateTime getDateToLocalDateTime(Date date) {
        if (date == null) return null;

        // Convert java.util.Date (UTC) -> Instant
        Instant instant = date.toInstant();

        // Convert Instant -> LocalDateTime in Asia/Ho_Chi_Minh timezone
        return instant.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
    }
}
