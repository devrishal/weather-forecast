package com.sapient.service.impl;

import com.sapient.service.WeatherForecastStrategy;
import com.sapient.service.helpers.WeatherAttributes;
import com.sapient.wfs.common.constants.MessageConstants;
import com.sapient.wfs.common.vo.ProcessedWeatherData;
import com.sapient.wfs.common.vo.WeatherData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WeatherForecastTemperatureServiceImpl implements WeatherForecastStrategy {
    @Override
    public void evaluateMessage(List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData) {
        processedWeatherData.setMessage(MessageConstants.HIGH_TEMPERATURE_MSG);
    }

    @Override
    public boolean supports(WeatherAttributes weatherAttributes) {
        return weatherAttributes.equals(WeatherAttributes.TEMPERATURE);
    }
}
