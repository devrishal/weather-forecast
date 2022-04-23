package com.wfs.api.rest;

import com.wfs.api.swagger.WeatherForecastResponse;
import com.wfs.utility.vo.Mode;
import com.wfs.utility.vo.ProcessedWeatherData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface WeatherForecastResource {
    @Operation(summary = "Get Weather forecast details by city name")
    @WeatherForecastResponse
    @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE, path = "/weather-data/{mode}")
    @PreAuthorize("hasAuthority('SUBSCRIBED_USER')")
    ResponseEntity<Collection<ProcessedWeatherData>> getWeatherForecastData(@RequestParam("city") @Parameter(description = "City name") String city, @DefaultValue("ONLINE") @PathVariable("mode") @Parameter(description = "Offline or Online Mode") Mode mode);
}
