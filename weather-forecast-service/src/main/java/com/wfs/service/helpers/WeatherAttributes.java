package com.wfs.service.helpers;

import com.wfs.utility.util.BeanUtil;
import com.wfs.utility.vo.ProcessedWeatherData;
import com.wfs.utility.vo.WeatherData;

import java.util.List;

public enum WeatherAttributes {
    TEMPERATURE {
        @Override
        public void getEvaluatedMessage(WeatherAttributes weatherAttributes, List<OperationMetadata> value, List<WeatherData> weatherDataList, ProcessedWeatherData
                processedWeatherData) {
            ExecuteWeatherForecastStrategy serviceName = BeanUtil.getBean(ExecuteWeatherForecastStrategy.class);
            serviceName.getProcessingService(weatherAttributes.name()).evaluateMessage(value, weatherDataList, processedWeatherData);
        }
    },
    WIND {
        @Override
        public void getEvaluatedMessage(WeatherAttributes weatherAttributes, List<OperationMetadata> value, List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData) {
            ExecuteWeatherForecastStrategy serviceName = BeanUtil.getBean(ExecuteWeatherForecastStrategy.class);
            serviceName.getProcessingService(weatherAttributes.name()).evaluateMessage(value,weatherDataList, processedWeatherData);
        }
    },
    WEATHER {
        @Override
        public void getEvaluatedMessage(WeatherAttributes weatherAttributes, List<OperationMetadata> value, List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData) {
            ExecuteWeatherForecastStrategy serviceName = BeanUtil.getBean(ExecuteWeatherForecastStrategy.class);
            serviceName.getProcessingService(weatherAttributes.name()).evaluateMessage(value,weatherDataList, processedWeatherData);
        }
    };


    public abstract void getEvaluatedMessage(WeatherAttributes weatherAttributes, List<OperationMetadata> value, List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData);
}
