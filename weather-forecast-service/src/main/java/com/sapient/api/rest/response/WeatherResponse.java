package com.sapient.api.rest.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WeatherResponse {
    private LocalDateTime date;
    private List<Double> temp;
    //private
}
