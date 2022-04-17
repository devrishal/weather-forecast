package com.sapient.service;

import com.sapient.wfs.common.vo.WeatherVO;

public interface WeatherForecastFetchService {
    public WeatherVO fetchWeatherDetails(String city);
}
