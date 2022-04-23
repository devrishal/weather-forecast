package com.wfs.service.impl;

import com.wfs.utility.vo.Mode;
import com.wfs.service.WeatherForecastFetchService;
import com.wfs.service.WeatherForecastProcessWeatherService;
import com.wfs.service.helpers.ConditionMetadataLoader;
import com.wfs.service.helpers.ExecuteWeatherForecastStrategy;
import com.wfs.service.helpers.WeatherAttributes;
import com.wfs.utility.config.PropertyConfig;
import com.wfs.utility.exception.WeatherForecastException;
import com.wfs.utility.util.DateUtil;
import com.wfs.utility.util.LRUCacheBuilder;
import com.wfs.utility.vo.Interval;
import com.wfs.utility.vo.ProcessedWeatherData;
import com.wfs.utility.vo.WeatherData;
import com.wfs.utility.vo.WeatherVO;
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
    public Collection<ProcessedWeatherData> doProcess(String city, Mode mode) {
        log.info("Calling WeatherForecast service API from {}", this.getClass().getName());
        WeatherVO weatherData = null;
        if (mode.equals(Mode.ONLINE)) {
            weatherData = weatherForecastFetchService.fetchWeatherDetails(city);
            log.info("Successful fetch from weather forecast service API");
            LRUCacheBuilder.getLruCache().put(city, weatherData);
        } else {
            weatherData = LRUCacheBuilder.getLruCache().get(city);
            log.info("Successful fetch from weather forecast cached service API");
        }

        if (null != weatherData) {
            Map<LocalDateTime, ProcessedWeatherData> result = processWeatherInformation(weatherData);
            return new ArrayList<>(result.values());
        } else {
            throw new WeatherForecastException("Error while fetching the data");
        }


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
                    if (weatherVO.getList().get(i).getDateText().isAfter(currentDate.minusSeconds(1))
                            && (weatherVO.getList().get(i).getDateText().isBefore(currentDate.plusDays(1)))) {
                        weatherDataList.add(weatherVO.getList().get(i));
                    }
                }
                if (!weatherDataList.isEmpty()) {
                    ProcessedWeatherData processedWeatherData = new ProcessedWeatherData();
                    populateIntervalData(currentDate, processedWeatherData);
                    evaluateInformationForMessage(weatherDataList, processedWeatherData);
                    resultMap.put(currentDate, processedWeatherData);
                }
                currentDate = currentDate.plusDays(1);
            }
        }
        return resultMap;
    }

    private void evaluateInformationForMessage(List<WeatherData> weatherDataList, ProcessedWeatherData processedWeatherData) {
        ConditionMetadataLoader.getConditionMap().entrySet().stream()
                .forEach(i -> WeatherAttributes.valueOf(i.getKey())
                        .getEvaluatedMessage(WeatherAttributes.valueOf(i.getKey()), i.getValue(), weatherDataList, processedWeatherData));
    }

    private void populateIntervalData(LocalDateTime currentDate, ProcessedWeatherData processedWeatherData) {
        Interval interval = new Interval();
        interval.setStart(DateUtil.startOfCurrentDay(currentDate));
        interval.setEnd(DateUtil.endOfCurrentDay(currentDate));
        processedWeatherData.setInterval(interval);
    }


}
