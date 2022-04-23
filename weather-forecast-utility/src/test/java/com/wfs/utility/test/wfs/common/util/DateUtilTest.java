package com.wfs.utility.test.wfs.common.util;

import com.wfs.utility.exception.WeatherForecastException;
import com.wfs.utility.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DateUtilTest {
    @Test
    void testEndOfCurrentDay() {
        LocalDateTime date = LocalDateTime.parse("2019-03-27T10:15:30");
        LocalDateTime expected = LocalDateTime.parse("2019-03-27T23:59:59.999999999");
        LocalDateTime actual = DateUtil.endOfCurrentDay(date);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testStartOfCurrentDay() {
        LocalDateTime date = LocalDateTime.parse("2019-03-27T10:15:30");
        LocalDateTime expected = LocalDateTime.parse("2019-03-27T00:00");
        LocalDateTime actual = DateUtil.startOfCurrentDay(date);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetIntervalInCurrentDay() {
        LocalDateTime date = LocalDateTime.parse("2019-03-27T10:15:30");
        int interval = DateUtil.getIntervalInCurrentDay(3, "HOURS", 1, date);
        Assertions.assertEquals(5, interval);

        interval = DateUtil.getIntervalInCurrentDay(1, "HOURS", 1, date);
        Assertions.assertEquals(14, interval);
        WeatherForecastException exception = Assertions.assertThrows(WeatherForecastException.class, () -> DateUtil.getIntervalInCurrentDay(3, null, 1, date));
        Assertions.assertEquals("Other Interval Mode not supported!", exception.getMessage());

        LocalDateTime newDate = LocalDateTime.parse("2022-04-21T00:00:00");
        interval = DateUtil.getIntervalInCurrentDay(3, "HOURS", 1, newDate);
        Assertions.assertEquals(8, interval);

        newDate = LocalDateTime.parse("2022-04-21T00:00:00");
        interval = DateUtil.getIntervalInCurrentDay(3, "HOURS", 3, newDate);
        Assertions.assertEquals(24, interval);

        newDate = LocalDateTime.parse("2022-04-21T18:00:00");
        interval = DateUtil.getIntervalInCurrentDay(3, "HOURS", 3, newDate);
        Assertions.assertEquals(18, interval);

        newDate = LocalDateTime.parse("2022-04-21T12:00:00");
        interval = DateUtil.getIntervalInCurrentDay(3, "HOURS", 3, newDate);
        Assertions.assertEquals(20, interval);


    }
}
