package com.wfs.service.impl;

import com.wfs.service.WeatherForecastStrategy;
import com.wfs.service.helpers.OperationMetadata;
import com.wfs.service.helpers.Operator;
import com.wfs.service.helpers.WeatherAttributes;
import com.wfs.utility.util.CommonUtil;
import com.wfs.utility.util.StaticDataLoader;
import com.wfs.utility.vo.ProcessedWeatherData;
import com.wfs.utility.vo.WeatherData;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherForecastWeatherTypeServiceImpl implements WeatherForecastStrategy {
    @Override
    public void evaluateMessage(List<OperationMetadata> operationMetadata, List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        operationMetadata.stream().forEach(operation -> {
            Optional<WeatherData> result = weatherDataList.stream().filter(i -> {
                Expression expression = expressionParser.parseExpression( "'"+operation.getOperandValue()+"'"+ "."+Operator.valueOf(operation.getOperator())+ "("  + "'"+i.getWeather().get(0).getMain()+"'"+")");
                return expression.getValue(Boolean.class);
            }).findFirst();
            if (result.isPresent() && CommonUtil.isMessageBlank(processedWeatherData.getMessage())) {
                populateMessage(processedWeatherData, result.get());
                return;
            }
        });

    }

    @Override
    public boolean supports(WeatherAttributes weatherAttributes) {
        return weatherAttributes.equals(WeatherAttributes.WEATHER);
    }

    private void populateMessage(ProcessedWeatherData processedWeatherData, WeatherData weatherData) {
        String weatherConditionID = String.valueOf(weatherData.getWeather().get(0).getId());
        processedWeatherData.setMessage(StaticDataLoader.getWeatherConditionCode().get(weatherConditionID).getMessage());
    }


}
