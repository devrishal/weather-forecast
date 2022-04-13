package com.sapient.wfs.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecastException extends RuntimeException {
    private WFError wfError;
}
