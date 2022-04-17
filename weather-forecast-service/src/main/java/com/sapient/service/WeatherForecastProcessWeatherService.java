package com.sapient.service;

import com.sapient.wfs.common.vo.ProcessedWeatherData;
import com.sapient.wfs.common.vo.WeatherVO;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public interface WeatherForecastProcessWeatherService {
    public Collection<ProcessedWeatherData> doProcess(String city);

    public Map<LocalDateTime, ProcessedWeatherData> processWeatherInformation(WeatherVO weatherVO);
}
