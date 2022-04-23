package com.wfs.service.helpers;

import com.wfs.service.WeatherForecastStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecuteWeatherForecastStrategy {
    private final PluginRegistry<WeatherForecastStrategy, WeatherAttributes> pluginRegistry;

    public WeatherForecastStrategy getProcessingService(String serviceName) {
        return pluginRegistry.getPluginFor(WeatherAttributes.valueOf(serviceName)).orElseThrow(() -> new RuntimeException("Payment method not supported"));
    }
}
