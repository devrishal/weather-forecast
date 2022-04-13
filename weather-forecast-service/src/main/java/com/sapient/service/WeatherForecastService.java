package com.sapient.service;

import com.sapient.service.helper.WeatherForecastServiceHelper;
import com.sapient.service.vo.*;
import com.sapient.wfs.common.consumers.WFConnectionUtility;
import com.sapient.wfs.common.util.WeatherConditionCode;
import com.sapient.wfs.common.util.WeatherConditionCodeLoader;
import com.sapient.wfs.common.util.WeatherType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sapient.wfs.common.constants.ApplicationConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherForecastService {

    @Value("${weather-forecast.service.url}")
    private String serviceUrl;
    @Value("${weather-forecast.service.appid}")
    private String appId;
    @Value("${weather-forecast.service.protocol}")
    private String protocol;
    @Value("${weather-forecast.duration}")
    private int weatherForecastDuration;
    @Value("${weather-forecast.time-interval}")
    private int weatherForecastTimeInterval;
    private final WFConnectionUtility connectionUtility;
    private final WeatherForecastServiceHelper weatherForecastServiceHelper;


    public WeatherVO getWeatherDetails(String city, int record_count) {
        String url = prepareUrl(city, record_count);
        WeatherVO weatherVO = connectionUtility.callService(url, connectionUtility.GET, constructHeaderMap(), WeatherVO.class).getBody();
        HashMap<LocalDate, List<ProcessedWeatherData>> map = new HashMap<>();
        System.out.println(weatherVO.getList().get(0).getDt_txt());
        List<ProcessedWeatherData> processedWeatherData = new ArrayList<>();
        for (int i = 0; i < weatherVO.getCnt(); i++) {
            WeatherData weatherData = weatherVO.getList().get(i);
            LocalDate date = weatherData.getDt_txt().toLocalDate();


            weatherData.getWeather();
            weatherData.getClouds();

        }
        return weatherVO;
    }

    private ProcessedWeatherData processWeatherData(WeatherData weatherData) {
        ProcessedWeatherData processedWeatherData = new ProcessedWeatherData();
        LocalDateTime dateTime = weatherData.getDt_txt();

        Interval interval = new Interval();
        interval.setEnd(dateTime.minusHours(weatherForecastTimeInterval));
        interval.setStart(dateTime);

        Temperature temperature = new Temperature();
        temperature.setMaximum(weatherData.getMain().getTemp_max());
        temperature.setMaximum(weatherData.getMain().getTemp_min());

        /**
         * API DOCUMENTATION
         * NOTE: It is possible to meet more than one weather condition for a requested location. The first weather condition in API respond is primary.
         */
        Weather weather = weatherData.getWeather().get(0);
        String suggestion = WeatherType.valueOf(weather.getMain()).getCustomDescription();
        processedWeatherData.setMessage(suggestion);
        return processedWeatherData;
    }


    private Map<String, String> constructHeaderMap() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headerMap.put("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headerMap;
    }

    private String prepareUrl(String city, int count) {
        StringBuilder appUrl = new StringBuilder().append(this.protocol).append(COLON).append(FWD_SLASH)
                .append(FWD_SLASH).append(this.serviceUrl).append(QUESTION_MARK).append(QUERY).append(EQUAL).append(city)
                .append(AND).append(APP_ID).append(EQUAL).append(this.appId).append(AND).append(RECORD_COUNT).append(EQUAL).append(count);
        return appUrl.toString();
    }

}
