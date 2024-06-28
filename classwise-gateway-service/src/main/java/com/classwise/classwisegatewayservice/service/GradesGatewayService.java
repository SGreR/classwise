package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.GradesDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GradesGatewayService implements ServiceInterface<GradesDTO> {
    
    private final RestTemplate restTemplate;

    public GradesGatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<GradesDTO> getAll() {
        String url = "http://localhost:8082/internal/grades";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(
                url,

                HttpMethod.GET,
                entity,
                List.class
        );

        return response.getBody();
    }

    @Override
    public GradesDTO getById(Long id) {
        String url = "http://localhost:8082/internal/grades/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<GradesDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                GradesDTO.class
        );

        return response.getBody();
    }

    @Override
    public GradesDTO add(GradesDTO grades) {
        String url = "http://localhost:8082/internal/grades";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GradesDTO> entity = new HttpEntity<>(grades, headers);

        ResponseEntity<GradesDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                GradesDTO.class
        );

        return response.getBody();
    }

    @Override
    public GradesDTO update(Long id, GradesDTO grades) {
        String url = "http://localhost:8082/internal/grades/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GradesDTO> entity = new HttpEntity<>(grades, headers);

        ResponseEntity<GradesDTO> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                GradesDTO.class
        );

        return response.getBody();
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        String url = "http://localhost:8082/internal/grades/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GradesDTO> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                GradesDTO.class
        );
    }
}
