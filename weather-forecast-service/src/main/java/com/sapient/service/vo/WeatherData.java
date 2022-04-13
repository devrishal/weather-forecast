package com.sapient.service.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WeatherData {
    private String dt;
    private Main main;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private String visibility;
    private Double pop;
    private Sys sys;
    private String dt_txt;
}
