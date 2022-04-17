package com.sapient.wfs.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "weather-forecast.service")
@Data
public class PropertyConfig {
    private String url;
    private String appId;
    private String protocol;
    private int durationDays;
    private int timeInterval;
    private String intervalMode;

}
