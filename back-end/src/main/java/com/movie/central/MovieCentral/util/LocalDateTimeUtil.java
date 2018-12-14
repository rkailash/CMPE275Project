package com.movie.central.MovieCentral.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTimeUtil {

    public static LocalDateTime getFirstDayOfGivenMonth(int month, int year) {
        LocalDateTime startDateTime = LocalDateTime.now(ZoneId.systemDefault()).withMonth(month).withYear(year).withHour(0).withMinute(0).withSecond(0).withNano(0);
        startDateTime = startDateTime.with(TemporalAdjusters.firstDayOfMonth());
        return startDateTime;
    }

    public static LocalDateTime getLastDayOfGivenMonth(LocalDateTime startDateTime) {
        LocalDateTime endDateTime = startDateTime.withHour(23).withMinute(59).withSecond(59);
        endDateTime = endDateTime.with(TemporalAdjusters.lastDayOfMonth());
        return endDateTime;
    }
}
