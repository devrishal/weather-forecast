package com.wfs.utility.util;

import com.wfs.utility.constants.ApplicationConstants;
import com.wfs.utility.exception.WeatherForecastException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateUtil {
    public static LocalDateTime startOfCurrentDay(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIDNIGHT);
    }

    public static LocalDateTime endOfCurrentDay(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().atTime(LocalTime.MAX);
    }

    public static int getIntervalInCurrentDay(int interval, String intervalMode, int durationDays, LocalDateTime currentDateTime) {
        int totalIntervalInDay = 0;
        if (null != intervalMode && intervalMode.equals(ApplicationConstants.HOURS))
            totalIntervalInDay = ((int) Duration.ofHours(24).toHours() / interval);
        else
            throw new WeatherForecastException("Other Interval Mode not supported!");
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

    private DateUtil() {
    }
}
