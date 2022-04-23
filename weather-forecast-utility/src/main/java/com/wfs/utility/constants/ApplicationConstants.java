package com.wfs.utility.constants;

public class ApplicationConstants {
    public static final String COLON = ":";
    public static final String QUERY = "q";
    public static final String EQUAL = "=";
    public static final String APP_ID = "appid";
    public static final String AND = "&";
    public static final String FWD_SLASH = "/";
    public static final String QUESTION_MARK = "?";
    public static final String RECORD_COUNT = "cnt";
    public static final String BLANK = "";
    public static final Double KELVIN_CONSTANT = 273.15;
    public static final Double WINDSPEED_CONSTANT_MPH = 2.236936;
    public static final String DECIMAL_FORMAT = "#.##";
    public static final String HOURS = "HOURS";
    public static final String APPID_MASKED_REGEX = "(appid)=[^&]+";
    public static final String APPID_MASKED_REPLACEMENT = "$1=XXXXXXXXXXXX";

    private ApplicationConstants() {
    }
}
