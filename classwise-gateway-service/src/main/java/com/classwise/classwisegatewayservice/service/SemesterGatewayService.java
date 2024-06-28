package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.SemesterDTO;
import com.classwise.classwisegatewayservice.model.SemesterDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SemesterGatewayService implements ServiceInterface<SemesterDTO> {

    private final RestTemplate restTemplate;

    public SemesterGatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<SemesterDTO> getAll() {
        String url = "http://localhost:8083/internal/semesters";
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
    public SemesterDTO getById(Long id) {
        String url = "http://localhost:8083/internal/semesters/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<SemesterDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                SemesterDTO.class
        );

        return response.getBody();
    }

    @Override
    public SemesterDTO add(SemesterDTO semester) {
        String url = "http://localhost:8083/internal/semesters";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SemesterDTO> entity = new HttpEntity<>(semester, headers);

        ResponseEntity<SemesterDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                SemesterDTO.class
        );

        return response.getBody();
    }

    @Override
    public SemesterDTO update(Long id, SemesterDTO semester) {
        String url = "http://localhost:8083/internal/semesters/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SemesterDTO> entity = new HttpEntity<>(semester, headers);

        ResponseEntity<SemesterDTO> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                SemesterDTO.class
        );

        return response.getBody();
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        String url = "http://localhost:8083/internal/semesters/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SemesterDTO> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                SemesterDTO.class
        );
    }
}
