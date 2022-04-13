package com.sapient.service.vo;

import lombok.Data;

@Data
public class ProcessedWeatherData {
    private Interval interval;
    private Temperature temperature;
    private String message;


}
