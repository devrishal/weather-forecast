package com.wfs.service;

import com.wfs.utility.vo.Mode;
import com.wfs.utility.vo.ProcessedWeatherData;
import com.wfs.utility.vo.WeatherVO;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

public interface WeatherForecastProcessWeatherService {
    public Collection<ProcessedWeatherData> doProcess(String city, Mode mode);

    public Map<LocalDateTime, ProcessedWeatherData> processWeatherInformation(WeatherVO weatherVO);
}
