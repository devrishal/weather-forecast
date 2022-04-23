package com.wfs.utility.vo;

import lombok.Data;

@Data
public class ProcessedWeatherData {
    private Interval interval;
    private double windSpeed;
    private Temperature temperature;
    private String message;


}
