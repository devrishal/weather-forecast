package com.wfs.utility.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WFError implements Serializable {
    private static final long serialVersionUID = -943011643890624811L;
    private int code;
    private String message;

    @JsonProperty("cod")
    public void setCode(int code) {
        this.code = code;
    }

    @JsonProperty("code")
    public int getCode() {
        return code;
    }
}
