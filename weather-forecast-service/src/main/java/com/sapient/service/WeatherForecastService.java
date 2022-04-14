package com.sapient.service;

import com.sapient.service.helper.WeatherForecastServiceHelper;
import com.sapient.wfs.common.algorithms.MinMaxAlgorithm;
import com.sapient.wfs.common.consumers.WFConnectionUtility;
import com.sapient.wfs.common.util.CommonUtil;
import com.sapient.wfs.common.vo.*;
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
    private final MinMaxAlgorithm minMaxAlgorithm;

    public List<ProcessedWeatherData> getWeatherDetails(String city, int record_count) {
        String url = prepareUrl(city, record_count);
        WeatherVO weatherVO = connectionUtility.callService(url, connectionUtility.GET, constructHeaderMap(), WeatherVO.class).getBody();
        log.info(weatherVO.toString());
        HashMap<LocalDate, ProcessedWeatherData> map = new HashMap<>();
        List<ProcessedWeatherData> processedWeatherDataList = new ArrayList<>();
        LocalDateTime currentDate = LocalDate.now().atStartOfDay();
        LocalDateTime calculatedDate = currentDate.plusDays(1);
        while (currentDate.isBefore(calculatedDate)) {
            for (int i = 0; i < weatherVO.getCnt(); i++) {
                if (weatherVO.getList().get(i).getDt_txt().isAfter(currentDate.minusSeconds(1)) && (weatherVO.getList().get(i).getDt_txt().isBefore(currentDate.plusDays(1)))) {
                    WeatherData weatherData = weatherVO.getList().get(i);
                    ProcessedWeatherData processedWeatherData = processWeatherData(weatherData);
                    processedWeatherDataList.add(processedWeatherData);
                }
            }
            ProcessedWeatherData processedWeatherData = populateDayWeatherData(currentDate, processedWeatherDataList);
            currentDate = currentDate.plusDays(1);

        }

        return processedWeatherDataList;
    }

    private ProcessedWeatherData populateDayWeatherData(LocalDateTime date, List<ProcessedWeatherData> processedWeatherDataList) {
        ProcessedWeatherData processedWeatherData = new ProcessedWeatherData();

        Interval interval = new Interval();
        interval.setStart(date);
        interval.setStart(date.plusDays(1).minusSeconds(1));
        processedWeatherData.setInterval(interval);

        //minMaxAlgorithm.getMinMax(processedWeatherDataList);

        Temperature temperature = new Temperature();
        temperature.setMaximum(minMaxAlgorithm.getTemp_max());
        temperature.setMinimum(minMaxAlgorithm.getTemp_min());

        String suggestion = "";//WeatherForecastServiceHelper.TEMPERATURE.getMessage(weatherData);
        processedWeatherData.setMessage(suggestion);
        return processedWeatherData;

    }

    private ProcessedWeatherData processWeatherData(WeatherData weatherData) {
        ProcessedWeatherData processedWeatherData = new ProcessedWeatherData();
        LocalDateTime dateTime = weatherData.getDt_txt();

        Interval interval = new Interval();
        interval.setStart(dateTime.equals(LocalDate.now().atStartOfDay()) ? dateTime : dateTime.minusHours(weatherForecastTimeInterval));
        interval.setEnd(dateTime);
        processedWeatherData.setInterval(interval);

        Temperature temperature = new Temperature();
        temperature.setMaximum(CommonUtil.kelvinToCelsius(weatherData.getMain().getTemp_max()));
        temperature.setMinimum(CommonUtil.kelvinToCelsius(weatherData.getMain().getTemp_min()));
        processedWeatherData.setTemperature(temperature);

        String suggestion = WeatherForecastServiceHelper.TEMPERATURE.getMessage(weatherData);
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
