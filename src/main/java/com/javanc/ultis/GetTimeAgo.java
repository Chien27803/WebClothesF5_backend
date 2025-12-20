package com.javanc.ultis;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class GetTimeAgo {

    public static String getTimeAgo(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();

        if (createdAt.isAfter(now)) {
            return "Vừa xong";
        }

        long years = ChronoUnit.YEARS.between(createdAt, now);
        if (years > 0) return years + " năm trước";

        long months = ChronoUnit.MONTHS.between(createdAt, now);
        if (months > 0) return months + " tháng trước";

        long days = ChronoUnit.DAYS.between(createdAt, now);
        if (days > 0) return days + " ngày trước";

        long hours = ChronoUnit.HOURS.between(createdAt, now);
        if (hours > 0) return hours + " giờ trước";

        long minutes = ChronoUnit.MINUTES.between(createdAt, now);
        if (minutes > 0) return minutes + " phút trước";

        long seconds = ChronoUnit.SECONDS.between(createdAt, now);
        if (seconds > 5) return seconds + " giây trước";

        return "Vừa xong";
    }
}
