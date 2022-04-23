package com.wfs.utility.test.wfs.common.algorithms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfs.utility.algorithms.MinMaxAlgorithm;
import com.wfs.utility.exception.WeatherForecastException;
import com.wfs.utility.vo.Temperature;
import com.wfs.utility.vo.WeatherData;
import com.wfs.utility.vo.WeatherVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class TestMinMaxAlgorithm {
    private WeatherVO weatherVO;

    @BeforeEach
    void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        try {
            InputStream input = TestMinMaxAlgorithm.class.getClassLoader().getResourceAsStream("test-response.json");
            weatherVO = objectMapper.readValue(input, WeatherVO.class);
        } catch (IOException e) {
            throw new WeatherForecastException(e.getMessage());
        }
    }

    @Test
    void testGetTemperatureMinMax() {
        List<WeatherData> weatherData = weatherVO.getList();
        Temperature temperature = MinMaxAlgorithm.getTemperatureMinMax(weatherData);
        Assertions.assertEquals(19.43, temperature.getMaximum());
        Assertions.assertEquals(8.77, temperature.getMinimum());
    }

    @Test
    void testGetMaxWindSpeed() {
        List<WeatherData> weatherData = weatherVO.getList();
        double maxWindSpeed = MinMaxAlgorithm.getMaxWindSpeed(weatherData);
        Assertions.assertEquals(13.33, maxWindSpeed);
    }

}
