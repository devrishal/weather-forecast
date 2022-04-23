package com.wfs.service;

import com.wfs.utility.vo.WeatherVO;

public interface WeatherForecastFetchService {
    public WeatherVO fetchWeatherDetails(String city);
}
