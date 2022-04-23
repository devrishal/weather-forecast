package com.wfs.utility.exception;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class WeatherForecastServiceException extends RuntimeException {
    private Integer code;
    private String message;

    public WeatherForecastServiceException(@NonNull String message, @NonNull int code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
