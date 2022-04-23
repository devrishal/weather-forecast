package com.wfs.utility.consumers;

import com.wfs.utility.exception.WFResponseErrorHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@SuppressWarnings({"unchecked", "rawTypes"})
@Slf4j
public class WFConnectionUtility {
    public static final String GET = "GET";

    private final RestTemplate restTemplate;

    public WFConnectionUtility(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> callService(@NonNull String httpUrl, @NonNull String httpMethodName, @NonNull Map<String, String> map, @NonNull Class<T> t) {
        HttpHeaders httpHeaders = getHttpHeaders(map);
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        HttpMethod httpMethod = HttpMethod.resolve(httpMethodName);
        HttpEntity entity = new HttpEntity(httpHeaders);
        restTemplate.setErrorHandler(new WFResponseErrorHandler());
        restTemplate.setRequestFactory(requestFactory);
        log.info("Calling Weather forecast service api");
        return restTemplate.exchange(httpUrl, httpMethod, entity, t, new Object[0]);
    }

    private HttpHeaders getHttpHeaders(Map<String, String> map) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", map.get("Content-Type"));
        httpHeaders.add("Accept", map.get("Accept"));
        return httpHeaders;
    }

}
