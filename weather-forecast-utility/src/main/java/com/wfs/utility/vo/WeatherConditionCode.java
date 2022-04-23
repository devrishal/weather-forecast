package com.wfs.utility.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherConditionCode {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("Main")
    private String main;
    @JsonProperty("Description")
    private String description;
    private String message;

}
