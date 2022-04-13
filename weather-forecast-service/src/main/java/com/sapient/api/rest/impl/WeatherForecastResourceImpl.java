package com.sapient.api.rest.impl;

import com.sapient.api.rest.WeatherForecastResource;
import com.sapient.service.WeatherForecastService;
import com.sapient.service.vo.WeatherVO;
import com.sun.org.apache.regexp.internal.RE;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
@RequestMapping("/v1")
@RequiredArgsConstructor
public class WeatherForecastResourceImpl implements WeatherForecastResource {
    private final WeatherForecastService weatherForecastService;

    @Override
    public ResponseEntity<WeatherVO> getWeatherForecastData(String city, int record_count) {
        return new ResponseEntity<>(weatherForecastService.getWeatherDetails(city, record_count), HttpStatus.OK);
    }
}
