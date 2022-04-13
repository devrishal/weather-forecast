package com.sapient.service.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Interval {
    @JsonProperty("dt")
    private LocalDateTime start;
    private LocalDateTime end;

}
