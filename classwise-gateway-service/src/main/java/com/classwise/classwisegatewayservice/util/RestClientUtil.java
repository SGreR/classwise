package com.classwise.classwisegatewayservice.util;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> entity, Class<T> responseType) {
        return restTemplate.exchange(url, method, entity, responseType);
    }

    public HttpEntity<?> createHttpEntity(Object body) {
        return new HttpEntity<>(body, createHeaders());
    }
}
