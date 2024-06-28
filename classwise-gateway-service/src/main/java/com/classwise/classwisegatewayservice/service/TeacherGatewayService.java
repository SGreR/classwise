package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.TeacherDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TeacherGatewayService implements ServiceInterface<TeacherDTO> {

    private final RestTemplate restTemplate;

    public TeacherGatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Override
    public List<TeacherDTO> getAll() {
        String url = "http://localhost:8085/internal/teachers";
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
    public TeacherDTO getById(Long id) {
        String url = "http://localhost:8085/internal/teachers/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<TeacherDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                TeacherDTO.class
        );

        return response.getBody();
    }

    @Override
    public TeacherDTO add(TeacherDTO teacher) {
        String url = "http://localhost:8085/internal/teachers";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TeacherDTO> entity = new HttpEntity<>(teacher, headers);

        ResponseEntity<TeacherDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                TeacherDTO.class
        );

        return response.getBody();
    }

    @Override
    public TeacherDTO update(Long id, TeacherDTO teacher) {
        String url = "http://localhost:8085/internal/teachers/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TeacherDTO> entity = new HttpEntity<>(teacher, headers);

        ResponseEntity<TeacherDTO> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                TeacherDTO.class
        );

        return response.getBody();
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        String url = "http://localhost:8085/internal/teachers/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TeacherDTO> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                TeacherDTO.class
        );
    }
}
