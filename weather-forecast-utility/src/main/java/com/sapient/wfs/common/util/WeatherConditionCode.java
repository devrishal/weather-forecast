package com.sapient.wfs.common.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherConditionCode {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("Main")
    private String main;
    @JsonProperty("Description")
    private String description;

}
