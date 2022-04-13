package com.sapient.wfs.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {
    private static final Long MILLIS_CONSTANT = 1000L;

    public static LocalDateTime convertUnixDateToLocalDate(Long date, String zone) {
        return Instant.ofEpochMilli(date * MILLIS_CONSTANT).atZone(ZoneId.of(zone)).toLocalDateTime();
    }
}
