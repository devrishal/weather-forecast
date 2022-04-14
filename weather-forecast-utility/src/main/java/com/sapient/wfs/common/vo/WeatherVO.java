package com.sapient.wfs.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class WeatherVO {
    private String cod;
    private String message;
    private int cnt;
    private List<WeatherData> list;
}
