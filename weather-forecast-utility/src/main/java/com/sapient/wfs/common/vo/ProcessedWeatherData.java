package com.sapient.wfs.common.vo;

import lombok.Data;

@Data
public class ProcessedWeatherData {
    private Interval interval;
    private Temperature temperature;
    private String message;


}
