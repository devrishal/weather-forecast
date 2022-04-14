package com.sapient.wfs.common.util;

import com.sapient.wfs.common.constants.ApplicationConstants;

import java.text.DecimalFormat;

public class CommonUtil {

    private static DecimalFormat df = new DecimalFormat(ApplicationConstants.DECIMAL_FORMAT);

    public static Double kelvinToCelsius(Double temperature) {
        return Double.valueOf(df.format(temperature - ApplicationConstants.KELVIN_CONSTANT));
    }

    public static Double meterPerSecToMPH(Double windSpeed) {
        return Double.valueOf(df.format(windSpeed * ApplicationConstants.WINDSPEED_CONSTANT_MPH));
    }
}
