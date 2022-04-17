package com.sapient.service.impl;

import com.sapient.service.WeatherForecastStrategy;
import com.sapient.service.helpers.WeatherAttributes;
import com.sapient.wfs.common.util.WeatherConditionCodeLoader;
import com.sapient.wfs.common.vo.ProcessedWeatherData;
import com.sapient.wfs.common.vo.WeatherData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherForecastWeatherTypeServiceImpl implements WeatherForecastStrategy {

    @Override
    public void evaluateMessage(List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData) {
        for (int i = 0; i < weatherDataList.size(); i++) {
            if (weatherDataList.get(i).getWeather() != null)
                if (weatherDataList.get(i).getWeather().get(0).getId() >= 500
                        && weatherDataList.get(i).getWeather().get(0).getId() <= 531) {
                    String weatherConditionID = String.valueOf(weatherDataList.get(i).getWeather().get(0).getId());
                    processedWeatherData.setMessage(WeatherConditionCodeLoader.weatherConditionCode.get(weatherConditionID).getMessage());
                    break;
                } else if (weatherDataList.get(i).getWeather().get(0).getId() >= 200
                        && weatherDataList.get(i).getWeather().get(0).getId() <= 232){
                    String weatherConditionID = String.valueOf(weatherDataList.get(i).getWeather().get(0).getId());
                    processedWeatherData.setMessage(WeatherConditionCodeLoader.weatherConditionCode.get(weatherConditionID).getMessage());
                }
        }
    }

    @Override
    public boolean supports(WeatherAttributes weatherAttributes) {
        return weatherAttributes.equals(WeatherAttributes.WEATHER);
    }
}
