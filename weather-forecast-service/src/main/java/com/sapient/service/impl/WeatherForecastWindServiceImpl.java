package com.sapient.service.impl;

import com.sapient.service.WeatherForecastStrategy;
import com.sapient.service.helpers.WeatherAttributes;
import com.sapient.wfs.common.util.CommonUtil;
import com.sapient.wfs.common.vo.ProcessedWeatherData;
import com.sapient.wfs.common.vo.WeatherData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherForecastWindServiceImpl implements WeatherForecastStrategy {
    @Override
    public void evaluateMessage(List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData) {
        for (int i = 0; i < weatherDataList.size(); i++) {
            if (CommonUtil.meterPerSecToMPH(weatherDataList.get(i).getWind().getSpeed()) > 10) {
                processedWeatherData.setMessage("It’s too windy, watch out!");
                break;
            }
        }
    }

    @Override
    public boolean supports(WeatherAttributes weatherAttributes) {
        return weatherAttributes.equals(WeatherAttributes.WIND);
    }
}
