package com.sapient.wfs.common.util;

import com.sapient.wfs.common.constants.ApplicationConstants;

import java.time.*;

public class DateUtil {
    private static final Long MILLIS_CONSTANT = 1000L;

    public static LocalDateTime convertUnixDateToLocalDate(Long date, String zone) {
        return Instant.ofEpochMilli(date * MILLIS_CONSTANT).atZone(ZoneId.of(zone)).toLocalDateTime();
    }

    public static LocalDateTime startOfCurrentDay(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIDNIGHT);
    }

    public static LocalDateTime endOfCurrentDay(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().atTime(LocalTime.MAX);
    }

    public static int getIntervalInCurrentDay(int interval, String intervalMode, int durationDays, LocalDateTime currentDateTime) {
        int totalIntervalInDay = 0;
        if (intervalMode.equals(ApplicationConstants.HOURS))
            totalIntervalInDay = ((int) Duration.ofHours(24).toHours() / interval);
        //Initial Interval Count as per 3 hours duration for next two complete days.
        int intervalCount = totalIntervalInDay * (durationDays - 1);
        LocalDateTime endOfDate = endOfCurrentDay(currentDateTime);
        for (int i = 1; i <= totalIntervalInDay; i++) {
            intervalCount++;
            if (currentDateTime.plusHours(interval).isBefore(endOfDate))
                currentDateTime = currentDateTime.plusHours(interval);
            else
                break;
        }
        return intervalCount;
    }
}
