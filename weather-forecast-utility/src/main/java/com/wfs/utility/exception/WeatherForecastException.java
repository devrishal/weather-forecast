package com.wfs.utility.exception;

import lombok.Getter;

@Getter
public class WeatherForecastException extends RuntimeException {
    private Integer code;
    private String message;

    public WeatherForecastException(String message) {
        super(message);
        this.message = message;
    }

    public WeatherForecastException(String message, int code) {
        this(message);
        this.code = code;
    }
}
