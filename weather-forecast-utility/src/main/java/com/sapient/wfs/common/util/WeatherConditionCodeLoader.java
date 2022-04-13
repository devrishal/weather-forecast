package com.sapient.wfs.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class WeatherConditionCodeLoader {
    @Autowired
    ResourceLoader resourceLoader;

    public static HashMap<String, WeatherConditionCode> weatherConditionCode;

    @Bean
    public Map<String, WeatherConditionCode> weatherConditionCodes() {
        Resource resource = resourceLoader.getResource("classpath:weather-codes.json");
        try {
            InputStream input = resource.getInputStream();
            File file = resource.getFile();
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<HashMap<String, WeatherConditionCode>> typeRef
                    = new TypeReference<HashMap<String, WeatherConditionCode>>() {
            };
            weatherConditionCode = objectMapper.readValue(file, typeRef);
            //weatherConditionCode = object;
            //weatherConditionCode.put(objectMapper.readValue(file, typeRef));
            log.info(weatherConditionCode.toString());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Unable to find weather condition codes properties");
        }
        return weatherConditionCode;
    }
}
