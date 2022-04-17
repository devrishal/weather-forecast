package com.sapient.wfs.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class WFResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST) || response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)
                || response.getStatusCode().equals(HttpStatus.FORBIDDEN) || response.getStatusCode().equals(HttpStatus.NOT_FOUND)
                || response.getStatusCode().equals(HttpStatus.METHOD_NOT_ALLOWED)) {
            return true;
        }
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.error("Response Error received: {}", response);
        ObjectMapper objectMapper = new ObjectMapper();
        WFError error = null;
        switch (response.getStatusCode()) {
            case BAD_REQUEST:
            case UNAUTHORIZED:
            case NOT_FOUND:
            case FORBIDDEN:
            case METHOD_NOT_ALLOWED:
                String json = new BufferedReader(
                        new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining());
                error = objectMapper.readValue(json, WFError.class);
                log.error("Response Error received: {}", error);
                throw new WeatherForecastException(error);
        }

    }
}
