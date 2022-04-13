package com.sapient.wfs.common.consumers;

import com.sapient.wfs.common.exception.WFResponseErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class WFConnectionUtility {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String GET = "GET";

    private final RestTemplate restTemplate;

    public WFConnectionUtility(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> callService(String httpUrl, String httpMethodName, Map<String, String> map, Class<T> t, Object... objects) {
        HttpHeaders httpHeaders = getHttpHeaders(map);
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        HttpMethod httpMethod = HttpMethod.resolve(httpMethodName);
        HttpEntity entity = new HttpEntity(httpHeaders);
        restTemplate.setErrorHandler(new WFResponseErrorHandler());
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate.exchange(httpUrl, httpMethod, entity, t, new Object[0]);
    }

    private HttpHeaders getHttpHeaders(Map<String, String> map) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", map.get("Content-Type"));
        httpHeaders.add("Accept", map.get("Accept"));
        return httpHeaders;
    }

}
