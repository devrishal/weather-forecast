package com.wfs.utility.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfs.utility.vo.WeatherConditionCode;
import com.wfs.utility.exception.WeatherForecastException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class StaticDataLoader {
    private static Map<String, WeatherConditionCode> weatherConditionCode;
    private final ObjectMapper objectMapper;

    public static Map<String, WeatherConditionCode> getWeatherConditionCode() {
        return weatherConditionCode;
    }

    private static void setWeatherConditionCode(Map<String, WeatherConditionCode> weatherConditionCode) {
        StaticDataLoader.weatherConditionCode = weatherConditionCode;
    }

    @Bean
    public void weatherConditionCodes() {
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("weather-codes.json");
            TypeReference<HashMap<String, WeatherConditionCode>> typeRef
                    = new TypeReference<HashMap<String, WeatherConditionCode>>() {
            };
            if (getWeatherConditionCode() == null)
                setWeatherConditionCode(objectMapper.readValue(input, typeRef));
            log.info(getWeatherConditionCode().toString());
        } catch (IOException e) {
            log.error("Unable to find weather condition codes properties");
            throw new WeatherForecastException("Unable to find weather condition codes properties");
        }
    }
}
