package com.wfs.api.rest.impl;

import com.wfs.api.rest.WeatherForecastResource;
import com.wfs.service.WeatherForecastProcessWeatherService;
import com.wfs.utility.vo.Mode;
import com.wfs.utility.vo.ProcessedWeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/v1")
@RequiredArgsConstructor
public class WeatherForecastResourceImpl implements WeatherForecastResource {
    private final WeatherForecastProcessWeatherService weatherForecastProcessWeatherService;

    @Override
    public ResponseEntity<Collection<ProcessedWeatherData>> getWeatherForecastData(String city, Mode mode) {
        return new ResponseEntity<>(weatherForecastProcessWeatherService.doProcess(city, mode), HttpStatus.OK);
    }
}
