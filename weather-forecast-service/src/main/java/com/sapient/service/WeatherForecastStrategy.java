package com.sapient.service;

import com.sapient.service.helpers.WeatherAttributes;
import com.sapient.wfs.common.vo.ProcessedWeatherData;
import com.sapient.wfs.common.vo.WeatherData;
import org.springframework.plugin.core.Plugin;

import java.util.List;

public interface WeatherForecastStrategy extends Plugin<WeatherAttributes> {
    public void evaluateMessage(List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData);
}
