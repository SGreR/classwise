package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.StudentDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

@Service
public class StudentGatewayService implements ServiceInterface<StudentDTO> {

    private final RestTemplate restTemplate;

    public StudentGatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<StudentDTO> getAll() {
        String url = "http://localhost:8084/internal/students";
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

    public StudentDTO getById(Long id) {
        String url = "http://localhost:8084/internal/students/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<StudentDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                StudentDTO.class
        );
        return response.getBody();
    }

    public List<StudentDTO> getMultipleByIds(List<Long> ids) {
        String url = "http://localhost:8084/internal/students/multiple";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Long>> entity = new HttpEntity<>(ids, headers);

        ResponseEntity<StudentDTO[]> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                StudentDTO[].class
        );

        return Arrays.asList(response.getBody());
    }

    public StudentDTO add(StudentDTO student) {
        String url = "http://localhost:8084/internal/students";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDTO> entity = new HttpEntity<>(student, headers);

        ResponseEntity<StudentDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                StudentDTO.class
        );

        return response.getBody();
    }

    public StudentDTO update(Long id, StudentDTO student) {
        String url = "http://localhost:8084/internal/students/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDTO> entity = new HttpEntity<>(student, headers);

        ResponseEntity<StudentDTO> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                StudentDTO.class
        );

        return response.getBody();
    }

    public ResponseEntity<?> deleteById(Long id) {
        String url = "http://localhost:8084/internal/students/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDTO> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                StudentDTO.class
        );
    }
}

