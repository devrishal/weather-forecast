package com.wfs.utility.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Temperature {

    @JsonProperty("temp_max")
    private Double maximum;
    @JsonProperty("temp_min")
    private Double minimum;
}
