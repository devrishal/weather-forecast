package com.wfs.service.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OperationMetadata {
    @JsonProperty("operation-name")
    private String operationName;
    private String operator;
    @JsonProperty("operand-value")
    private String operandValue;
    @JsonProperty("operand-type")
    private String operandType;
    private int order;
}
