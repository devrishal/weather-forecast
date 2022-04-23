package com.wfs.test.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfs.service.WeatherForecastFetchService;
import com.wfs.service.WeatherForecastProcessWeatherService;
import com.wfs.utility.util.BeanUtil;
import com.wfs.service.helpers.ConditionMetadataLoader;
import com.wfs.service.helpers.ExecuteWeatherForecastStrategy;
import com.wfs.service.helpers.WeatherAttributes;
import com.wfs.service.impl.*;
import com.wfs.utility.config.PropertyConfig;
import com.wfs.utility.consumers.WFConnectionUtility;
import com.wfs.utility.util.LRUCacheBuilder;
import com.wfs.utility.util.StaticDataLoader;
import com.wfs.utility.vo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WeatherForecastProcessServiceImplTest {
    private WeatherForecastProcessWeatherService weatherForecastProcessService;
    @Mock
    private WeatherForecastFetchService weatherForecastFetchService;
    @Mock
    private ExecuteWeatherForecastStrategy executeWeatherForecastStrategy;
    @Mock
    private PropertyConfig propertyConfig;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private WFConnectionUtility wfConnectionUtility;
    @Spy
    private BeanUtil beanUtil;
    @Mock
    private ApplicationContext applicationContext;
    @Spy
    List<ProcessedWeatherData> processedWeatherData = new ArrayList<>();
    @Spy
    WeatherVO weatherVO = new WeatherVO();

    private StaticDataLoader staticDataLoader;
    private ConditionMetadataLoader conditionMetadataLoader;
    private LRUCacheBuilder lruCacheBuilder;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        beanUtil.setApplicationContext(applicationContext);
        lruCacheBuilder = new LRUCacheBuilder();
        lruCacheBuilder.build();
        staticDataLoader = new StaticDataLoader(objectMapper);
        staticDataLoader.weatherConditionCodes();
        conditionMetadataLoader = new ConditionMetadataLoader(objectMapper);
        conditionMetadataLoader.initConditionsMetaData();
        Mockito.when(propertyConfig.getDurationDays()).thenReturn(3);
        weatherVO.setCnt(1);
        weatherVO.setCod("200");
        weatherVO.setMessage("0");
        when(propertyConfig.getTimeInterval()).thenReturn(3);
        when(propertyConfig.getIntervalMode()).thenReturn("HOURS");
        when(propertyConfig.getDurationDays()).thenReturn(3);
        when(propertyConfig.getProtocol()).thenReturn("https");
        when(propertyConfig.getUrl()).thenReturn("test.api.openweathermap.org/data/2.5/forecast");
        when(propertyConfig.getAppId()).thenReturn("ihUYB1qK5Zyy3LAueTpnXV175v1NS47AHro1vJaZrt4=");
        ResponseEntity<WeatherVO> response = new ResponseEntity<>(weatherVO, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), Mockito.eq(HttpMethod.GET),
                any(HttpEntity.class),
                Mockito.eq(WeatherVO.class)))
                .thenReturn(response);
        weatherForecastFetchService = new WeatherForecastFetchServiceImpl(wfConnectionUtility, propertyConfig);
        weatherForecastProcessService = new WeatherForecastProcessServiceImpl(weatherForecastFetchService, executeWeatherForecastStrategy, propertyConfig);
        Mockito.when(executeWeatherForecastStrategy.getProcessingService(WeatherAttributes.TEMPERATURE.name())).thenReturn(new WeatherForecastTemperatureServiceImpl());
        Mockito.when(executeWeatherForecastStrategy.getProcessingService(WeatherAttributes.WEATHER.name())).thenReturn(new WeatherForecastWeatherTypeServiceImpl());
        Mockito.when(executeWeatherForecastStrategy.getProcessingService(WeatherAttributes.WIND.name())).thenReturn(new WeatherForecastWindServiceImpl());
        Mockito.when(BeanUtil.getBean(any())).thenReturn(executeWeatherForecastStrategy);



    }

    @Test
    void testDoProcess_Temperature() {
        Main main = new Main();
        main.setTempMax(314.00);
        main.setTempMin(288.21);

        WeatherData weatherData = new WeatherData();
        weatherData.setMain(main);

        Weather weather = new Weather();
        weather.setId(200);
        weather.setMain("Thunderstorm");
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        weatherData.setWeather(weatherList);

        Wind wind = new Wind();
        wind.setSpeed(25.00);
        weatherData.setWind(wind);

        weatherData.setDateText(LocalDateTime.now());
        List<WeatherData> weatherDataList = new ArrayList<>();
        weatherDataList.add(weatherData);
        weatherVO.setList(weatherDataList);
        Collection<ProcessedWeatherData> processedWeatherData = weatherForecastProcessService.doProcess("london", Mode.ONLINE);
        Assertions.assertEquals("Use sunscreen lotion", ((List<ProcessedWeatherData>) processedWeatherData).get(0).getMessage());
    }

    @Test
    void testDoProcess_Weather() {
        Main main = new Main();
        main.setTempMax(110.00);
        main.setTempMin(100.00);

        WeatherData weatherData = new WeatherData();
        weatherData.setMain(main);

        Weather weather = new Weather();
        weather.setId(200);
        weather.setMain("Thunderstorm");
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        weatherData.setWeather(weatherList);

        Wind wind = new Wind();
        wind.setSpeed(25.00);
        weatherData.setWind(wind);

        weatherData.setDateText(LocalDateTime.now());
        List<WeatherData> weatherDataList = new ArrayList<>();
        weatherDataList.add(weatherData);
        weatherVO.setList(weatherDataList);
        Collection<ProcessedWeatherData> processedWeatherData = weatherForecastProcessService.doProcess("london", Mode.ONLINE);
        Assertions.assertEquals("Don’t step out! A Storm is brewing!", ((List<ProcessedWeatherData>) processedWeatherData).get(0).getMessage());

    }

    @Test
    void testDoProcess_Wind() {
        Main main = new Main();
        main.setTempMax(120.00);
        main.setTempMin(110.00);

        WeatherData weatherData = new WeatherData();
        weatherData.setMain(main);

        Weather weather = new Weather();
        weather.setId(310);
        weather.setMain("Drizzle");
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        weatherData.setWeather(weatherList);

        Wind wind = new Wind();
        wind.setSpeed(25.00);
        weatherData.setWind(wind);

        weatherData.setDateText(LocalDateTime.now());
        List<WeatherData> weatherDataList = new ArrayList<>();
        weatherDataList.add(weatherData);
        weatherVO.setList(weatherDataList);
        Collection<ProcessedWeatherData> processedWeatherData = weatherForecastProcessService.doProcess("london", Mode.ONLINE);
        Assertions.assertEquals("It’s too windy, watch out!", ((List<ProcessedWeatherData>) processedWeatherData).get(0).getMessage());
    }
}
