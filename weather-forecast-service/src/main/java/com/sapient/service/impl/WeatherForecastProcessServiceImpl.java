package com.sapient.service.impl;

import com.sapient.service.WeatherForecastFetchService;
import com.sapient.service.WeatherForecastProcessWeatherService;
import com.sapient.service.helpers.ExecuteWeatherForecastStrategy;
import com.sapient.service.helpers.WeatherAttributes;
import com.sapient.wfs.common.algorithms.MinMaxAlgorithm;
import com.sapient.wfs.common.config.PropertyConfig;
import com.sapient.wfs.common.util.CommonUtil;
import com.sapient.wfs.common.util.DateUtil;
import com.sapient.wfs.common.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherForecastProcessServiceImpl implements WeatherForecastProcessWeatherService {

    private final WeatherForecastFetchService weatherForecastFetchService;
    private final ExecuteWeatherForecastStrategy executeWeatherForecastStrategy;
    private final PropertyConfig propertyConfig;

    @Override
    public Collection<ProcessedWeatherData> doProcess(String city) {
        log.info("Calling WeatherForecast service API from {}", this.getClass().getName());
        WeatherVO weatherData = weatherForecastFetchService.fetchWeatherDetails(city);
        log.info("Successful fetch from weather forecast service API");
        Map<LocalDateTime, ProcessedWeatherData> result = processWeatherInformation(weatherData);
        return result.values();
    }

    public Map<LocalDateTime, ProcessedWeatherData> processWeatherInformation(WeatherVO weatherVO) {
        LocalDateTime currentDate = LocalDate.now().atStartOfDay();
        //Inclusive of current day
        LocalDateTime calculatedDate = currentDate.plusDays(propertyConfig.getDurationDays());
        Map<LocalDateTime, ProcessedWeatherData> resultMap = new HashMap<>();
        if (weatherVO != null) {
            while (currentDate.isBefore(calculatedDate)) {
                List<WeatherData> weatherDataList = new ArrayList<>();
                for (int i = 0; i < weatherVO.getCnt(); i++) {
                    if (weatherVO.getList().get(i).getDt_txt().isAfter(currentDate.minusSeconds(1))
                            && (weatherVO.getList().get(i).getDt_txt().isBefore(currentDate.plusDays(1)))) {
                        weatherDataList.add(weatherVO.getList().get(i));
                    }
                }
                ProcessedWeatherData processedWeatherData = new ProcessedWeatherData();
                populateIntervalData(currentDate, processedWeatherData);
                evaluateInformationForMessage(weatherDataList, processedWeatherData);
                resultMap.put(currentDate, processedWeatherData);
                currentDate = currentDate.plusDays(1);
            }
        }
        return resultMap;
    }

    private void evaluateInformationForMessage(List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData) {
        Temperature temperature = MinMaxAlgorithm.getMinMax(weatherDataList);
        processedWeatherData.setTemperature(temperature);
        if (temperature.getMaximum() > 40) {
            executeWeatherForecastStrategy.getProcessingService(WeatherAttributes.TEMPERATURE.name()).evaluateMessage(weatherDataList, processedWeatherData);
        } else if (CommonUtil.isMessageBlank(processedWeatherData.getMessage())) {
            executeWeatherForecastStrategy.getProcessingService(WeatherAttributes.WEATHER.name()).evaluateMessage(weatherDataList, processedWeatherData);
        } else if (CommonUtil.isMessageBlank(processedWeatherData.getMessage())) {
            executeWeatherForecastStrategy.getProcessingService(WeatherAttributes.WIND.name()).evaluateMessage(weatherDataList, processedWeatherData);
        }
    }

    private void populateIntervalData(LocalDateTime currentDate, ProcessedWeatherData processedWeatherData) {
        Interval interval = new Interval();
        interval.setStart(DateUtil.startOfCurrentDay(currentDate));
        interval.setEnd(DateUtil.endOfCurrentDay(currentDate));
        processedWeatherData.setInterval(interval);
    }
}
