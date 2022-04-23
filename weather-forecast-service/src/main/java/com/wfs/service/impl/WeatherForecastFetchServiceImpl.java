package com.wfs.service.impl;

import com.wfs.service.WeatherForecastFetchService;
import com.wfs.utility.algorithms.DecryptUtil;
import com.wfs.utility.config.PropertyConfig;
import com.wfs.utility.consumers.WFConnectionUtility;
import com.wfs.utility.util.DateUtil;
import com.wfs.utility.vo.WeatherVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.wfs.utility.constants.ApplicationConstants.*;

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
        String appId = DecryptUtil.decrypt(propertyConfig.getAppId());
        WeatherVO weatherVO = connectionUtility.callService(prepareUrl(city, recordCount, appId), WFConnectionUtility.GET, constructHeaderMap(), WeatherVO.class).getBody();
        log.info("After calling weather forecast service api");
        return weatherVO;
    }

    private Map<String, String> constructHeaderMap() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headerMap.put("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headerMap;
    }

    private String prepareUrl(String city, int count, String appId) {
        log.info("Preparing Weather service url");
        StringBuilder appUrl = new StringBuilder().append(propertyConfig.getProtocol()).append(COLON).append(FWD_SLASH)
                .append(FWD_SLASH).append(propertyConfig.getUrl())
                .append(QUESTION_MARK).append(QUERY)
                .append(EQUAL).append(city).append(AND)
                .append(APP_ID).append(EQUAL)
                .append(appId)
                .append(AND).append(RECORD_COUNT)
                .append(EQUAL).append(count);
        String finalUrl = appUrl.toString();
        log.info("Calling Weather service url: {}", maskedUrl(finalUrl));
        return finalUrl;
    }

    private String maskedUrl(String appUrl) {
        return appUrl.replaceAll(APPID_MASKED_REGEX, APPID_MASKED_REPLACEMENT);
    }
}

