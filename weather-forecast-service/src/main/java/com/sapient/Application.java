package com.sapient;

import com.sapient.service.WeatherForecastStrategy;
import com.sapient.wfs.common.config.PropertyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.plugin.core.config.EnablePluginRegistries;

@SpringBootApplication
@EnableConfigurationProperties(PropertyConfig.class)
@EnablePluginRegistries(WeatherForecastStrategy.class)
public class Application {
    public static void main(String[] args) {
        try {
            SpringApplication.run(Application.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
