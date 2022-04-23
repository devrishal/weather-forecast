package com.wfs.test.service.impl;

import com.wfs.service.impl.WeatherForecastFetchServiceImpl;
import com.wfs.utility.config.PropertyConfig;
import com.wfs.utility.consumers.WFConnectionUtility;
import com.wfs.utility.vo.WeatherVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WeatherForecastFetchServiceImplTest {
    private WeatherForecastFetchServiceImpl weatherForecastFetchService;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private WFConnectionUtility wfConnectionUtility;
    @Mock
    private PropertyConfig propertyConfig;

    @BeforeEach
    void setUp() {
        WeatherVO weatherVO = mock(WeatherVO.class);
        when(weatherVO.getCod()).thenReturn("200");
        when(weatherVO.getMessage()).thenReturn("0");
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
    }

    @Test
    void testFetchWeatherDetails() {
        WeatherVO weatherVO = weatherForecastFetchService.fetchWeatherDetails("london");
        Assertions.assertEquals(0, weatherVO.getCnt());
        Assertions.assertEquals("0", weatherVO.getMessage());
        Assertions.assertEquals("200", weatherVO.getCod());
    }

}
