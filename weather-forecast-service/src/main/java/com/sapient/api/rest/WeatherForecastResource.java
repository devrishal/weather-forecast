package com.sapient.api.rest;

import com.sapient.api.swagger.WeatherForecastResponse;
import com.sapient.service.vo.WeatherVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface WeatherForecastResource {
    @Operation(summary = "Get Weather forecast details by city name")
    @WeatherForecastResponse
    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE, path = "/weather-data")
    ResponseEntity<WeatherVO> getWeatherForecastData(@RequestParam("city") String city, @RequestParam("record-count") int record_count);
}