package com.wfs.service.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfs.utility.exception.WeatherForecastException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ConditionMetadataLoader {
    private final ObjectMapper objectMapper;
    private static LinkedHashMap<String, List<OperationMetadata>> conditionMap;

    public static Map<String, List<OperationMetadata>> getConditionMap() {
        return conditionMap;
    }

    private static void setConditionMap(LinkedHashMap<String, List<OperationMetadata>> conditionMap) {
        ConditionMetadataLoader.conditionMap = conditionMap;
    }

    @Bean
    public void initConditionsMetaData() {
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("operation-metadata.json");
            TypeReference<List<OperationMetadata>> typeRef
                    = new TypeReference<List<OperationMetadata>>() {
            };
            setConditionMap(objectMapper.readValue(input, typeRef).stream()
                    .collect(Collectors.groupingBy(OperationMetadata::getOperationName)).entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.comparing(i -> i.get(0).getOrder())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new)));
        } catch (IOException e) {
            log.error("Unable to find weather condition codes properties", e);
            throw new WeatherForecastException("Unable to find weather condition codes properties");
        }
    }


}
