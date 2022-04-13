package com.sapient.service;

import com.sapient.service.vo.WeatherVO;
import com.sapient.wfs.common.consumers.WFConnectionUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.HashMap;
import java.util.Map;

import static com.sapient.wfs.common.constants.ApplicationConstants.*;

@Service
@Log
@RequiredArgsConstructor
public class WeatherForecastService {

    @Value("${weather-forecast.service.url}")
    private String serviceUrl;
    @Value("${weather-forecast.service.appid}")
    private String appId;
    @Value("${weather-forecast.service.protocol}")
    private String protocol;

    private final WFConnectionUtility connectionUtility;


    public WeatherVO getWeatherDetails(String city,int record_count) {
        String url = prepareUrl(city,record_count);
        return connectionUtility.callService(url, connectionUtility.GET, constructHeaderMap(), WeatherVO.class).getBody();
    }

    private Map<String, String> constructHeaderMap() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headerMap.put("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headerMap;
    }

    private String prepareUrl(String city,int count) {
        StringBuilder appUrl = new StringBuilder().append(this.protocol).append(COLON).append(FWD_SLASH)
                .append(FWD_SLASH).append(this.serviceUrl).append(QUESTION_MARK).append(QUERY).append(EQUAL).append(city)
                .append(AND).append(APP_ID).append(EQUAL).append(this.appId).append(AND).append(RECORD_COUNT).append(EQUAL).append(count);
        return appUrl.toString();
    }

}
