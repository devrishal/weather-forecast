package com.sapient.api.rest.impl;

import com.sapient.api.rest.WeatherForecastResource;
import com.sapient.service.WeatherForecastProcessWeatherService;
import com.sapient.wfs.common.vo.ProcessedWeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1")
@RequiredArgsConstructor
public class WeatherForecastResourceImpl implements WeatherForecastResource {
    private final WeatherForecastProcessWeatherService weatherForecastProcessWeatherService;

    @Override
    public ResponseEntity<Collection<ProcessedWeatherData>> getWeatherForecastData(String city) {
        return new ResponseEntity<Collection<ProcessedWeatherData>>(weatherForecastProcessWeatherService.doProcess(city), HttpStatus.OK);
    }
}
