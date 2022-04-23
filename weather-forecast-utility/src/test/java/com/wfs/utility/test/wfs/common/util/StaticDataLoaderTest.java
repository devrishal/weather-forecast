package com.wfs.utility.test.wfs.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfs.utility.vo.WeatherConditionCode;
import com.wfs.utility.util.StaticDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class StaticDataLoaderTest {
    private StaticDataLoader staticDataLoader;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        staticDataLoader = new StaticDataLoader(objectMapper);
        staticDataLoader.weatherConditionCodes();
    }

    @Test
    void testWeatherConditionCodes() {
        Map<String, WeatherConditionCode> weatherConditionCodeMap = StaticDataLoader.getWeatherConditionCode();
        Assertions.assertEquals("Don’t step out! A Storm is brewing!", weatherConditionCodeMap.get("200").getMessage());
        Assertions.assertEquals("Drizzle!", weatherConditionCodeMap.get("314").getMessage());
        Assertions.assertEquals("Carry umbrella!", weatherConditionCodeMap.get("501").getMessage());
        Assertions.assertEquals("Carry umbrella!", weatherConditionCodeMap.get("511").getMessage());
        Assertions.assertEquals("Wear Sweater and Jackets!", weatherConditionCodeMap.get("600").getMessage());
        Assertions.assertEquals("Wear Sweater and Jackets!", weatherConditionCodeMap.get("620").getMessage());
        Assertions.assertEquals("Haze!", weatherConditionCodeMap.get("721").getMessage());
        Assertions.assertEquals("Sand!", weatherConditionCodeMap.get("751").getMessage());
        Assertions.assertEquals("Clear sky!", weatherConditionCodeMap.get("800").getMessage());
        Assertions.assertEquals("Broken clouds!", weatherConditionCodeMap.get("803").getMessage());
    }
}
