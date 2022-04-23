package com.wfs.service.impl;

import com.wfs.service.WeatherForecastStrategy;
import com.wfs.service.helpers.OperationMetadata;
import com.wfs.service.helpers.Operator;
import com.wfs.service.helpers.WeatherAttributes;
import com.wfs.utility.algorithms.MinMaxAlgorithm;
import com.wfs.utility.constants.MessageConstants;
import com.wfs.utility.util.CommonUtil;
import com.wfs.utility.vo.ProcessedWeatherData;
import com.wfs.utility.vo.Temperature;
import com.wfs.utility.vo.WeatherData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WeatherForecastTemperatureServiceImpl implements WeatherForecastStrategy {
    @Override
    public boolean supports(WeatherAttributes weatherAttributes) {
        return weatherAttributes.equals(WeatherAttributes.TEMPERATURE);
    }

    @Override
    public void evaluateMessage(List<OperationMetadata> operationMetadata, List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Temperature temperature = MinMaxAlgorithm.getTemperatureMinMax(weatherDataList);
        Optional<OperationMetadata> result = operationMetadata.stream().filter(i -> {
            Expression expression = expressionParser.parseExpression(temperature.getMaximum() + " " + Operator.valueOf(i.getOperator()) + " " + i.getOperandValue());
            return expression.getValue(Boolean.class);
        }).findFirst();
        if (result.isPresent() && CommonUtil.isMessageBlank(processedWeatherData.getMessage())) {
            processedWeatherData.setMessage(MessageConstants.HIGH_TEMPERATURE_MSG);
        }
        processedWeatherData.setTemperature(temperature);

    }


}
