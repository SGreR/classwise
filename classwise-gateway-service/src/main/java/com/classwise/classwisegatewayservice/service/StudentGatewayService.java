package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.model.StudentDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;

@Service
public class StudentGatewayService {

    private final RestTemplate restTemplate;

    public StudentGatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<StudentDTO> getAllStudents() {
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

    public StudentDTO getStudentById(Long id) {
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

    public StudentDTO addStudent(StudentDTO student) {
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

    public StudentDTO updateStudent(Long id, StudentDTO student) {
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

    public StudentDTO deleteStudentById(Long id) {
        String url = "http://localhost:8084/internal/students/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<StudentDTO> entity = new HttpEntity<>(headers);

        ResponseEntity<StudentDTO> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                StudentDTO.class
        );

        return response.getBody();
    }
}

