package com.wfs.service;

import com.wfs.service.helpers.OperationMetadata;
import com.wfs.service.helpers.WeatherAttributes;
import com.wfs.utility.vo.ProcessedWeatherData;
import com.wfs.utility.vo.WeatherData;
import org.springframework.plugin.core.Plugin;

import java.util.List;

public interface WeatherForecastStrategy extends Plugin<WeatherAttributes> {
    public void evaluateMessage(List<OperationMetadata> operationMetadata, List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData);
}
