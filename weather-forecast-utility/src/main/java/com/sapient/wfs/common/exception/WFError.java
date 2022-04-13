package com.sapient.wfs.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WFError {
    private String code;
    private String message;

    @JsonProperty("cod")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }
}
