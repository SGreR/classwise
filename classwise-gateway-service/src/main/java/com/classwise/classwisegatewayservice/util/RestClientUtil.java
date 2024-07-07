package com.classwise.classwisegatewayservice.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestClientUtil {

    private final RestTemplate restTemplate;

    public RestClientUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> entity,  Class<T> responseType) {
        return restTemplate.exchange(url, method, entity, responseType);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> entity,  ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(url, method, entity, responseType);
    }

    public <T> HttpEntity<T> createHttpEntity(Object body, MultiValueMap<String, String> additionalHeaders) {
        HttpHeaders headers = createHeaders();

        if (additionalHeaders != null) {
            headers.addAll(additionalHeaders);
        }

        return (HttpEntity<T>) new HttpEntity<>(body, headers);
    }

    public <T> HttpEntity<T> createHttpEntity(Object body) {
        return createHttpEntity(body, null);
    }
}
