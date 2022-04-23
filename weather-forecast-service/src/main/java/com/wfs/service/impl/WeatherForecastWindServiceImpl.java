package com.wfs.service.impl;

import com.wfs.service.WeatherForecastStrategy;
import com.wfs.service.helpers.OperationMetadata;
import com.wfs.service.helpers.Operator;
import com.wfs.service.helpers.WeatherAttributes;
import com.wfs.utility.algorithms.MinMaxAlgorithm;
import com.wfs.utility.util.CommonUtil;
import com.wfs.utility.vo.ProcessedWeatherData;
import com.wfs.utility.vo.WeatherData;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherForecastWindServiceImpl implements WeatherForecastStrategy {
    @Override
    public void evaluateMessage(List<OperationMetadata> operationMetadata, List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData) {
        double windSpeed = MinMaxAlgorithm.getMaxWindSpeed(weatherDataList);
        processedWeatherData.setWindSpeed(windSpeed);
        ExpressionParser expressionParser = new SpelExpressionParser();
        Optional<OperationMetadata> result = operationMetadata.stream().filter(i -> {
            Expression expression = expressionParser.parseExpression(windSpeed + " " + Operator.valueOf(i.getOperator()) + " " + i.getOperandValue());
            return expression.getValue(Boolean.class);
        }).findFirst();
        if (result.isPresent() && CommonUtil.isMessageBlank(processedWeatherData.getMessage())) {
            processedWeatherData.setMessage("It’s too windy, watch out!");
        }


    }

    @Override
    public boolean supports(WeatherAttributes weatherAttributes) {
        return weatherAttributes.equals(WeatherAttributes.WIND);
    }
}
