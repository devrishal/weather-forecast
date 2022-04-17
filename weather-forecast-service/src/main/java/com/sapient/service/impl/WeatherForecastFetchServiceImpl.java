package com.sapient.service.impl;

import com.sapient.service.WeatherForecastFetchService;
import com.sapient.wfs.common.config.PropertyConfig;
import com.sapient.wfs.common.consumers.WFConnectionUtility;
import com.sapient.wfs.common.util.DateUtil;
import com.sapient.wfs.common.vo.WeatherVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.sapient.wfs.common.constants.ApplicationConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherForecastFetchServiceImpl implements WeatherForecastFetchService {
    private final WFConnectionUtility connectionUtility;
    private final PropertyConfig propertyConfig;

    @Override
    public WeatherVO fetchWeatherDetails(String city) {
        log.info("Inside {}, calling weather forecast service", this.getClass().getName());
        int recordCount = DateUtil.getIntervalInCurrentDay(propertyConfig.getTimeInterval(), propertyConfig.getIntervalMode(), propertyConfig.getDurationDays(), LocalDateTime.now());
        WeatherVO weatherVO = connectionUtility.callService(prepareUrl(city, recordCount), connectionUtility.GET, constructHeaderMap(), WeatherVO.class).getBody();
        log.info("After calling weather forecast service api");
        return weatherVO;
    }

    private Map<String, String> constructHeaderMap() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headerMap.put("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headerMap;
    }

    private String prepareUrl(String city, int count) {
        StringBuilder appUrl = new StringBuilder().append(propertyConfig.getProtocol()).append(COLON).append(FWD_SLASH)
                .append(FWD_SLASH).append(propertyConfig.getUrl()).append(QUESTION_MARK).append(QUERY).append(EQUAL).append(city)
                .append(AND).append(APP_ID).append(EQUAL).append(propertyConfig.getAppId())
                .append(AND).append(RECORD_COUNT).append(EQUAL).append(count);
        log.info("Calling Weather service url:{}", appUrl);
        return appUrl.toString();
    }
}

