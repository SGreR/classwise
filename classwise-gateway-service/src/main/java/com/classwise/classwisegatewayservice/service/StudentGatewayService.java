package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.filters.StudentDTOFilter;
import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.CourseDTO;
import com.classwise.classwisegatewayservice.model.StudentDTO;
import com.classwise.classwisegatewayservice.util.RestClientUtil;
import com.classwise.classwisegatewayservice.util.ServiceURLs;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import java.util.List;

@Service
public class StudentGatewayService implements ServiceInterface<StudentDTO> {

    private final RestClientUtil restClientUtil;
    private final ServiceURLs serviceURLs;

    public StudentGatewayService(RestClientUtil restClientUtil, ServiceURLs serviceURLs) {
        this.restClientUtil = restClientUtil;
        this.serviceURLs = serviceURLs;
    }

    public List<StudentDTO> getAll() {
        String url = serviceURLs.getStudentsUrl();
        ResponseEntity<List> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), List.class);
        return response.getBody();
    }

    public StudentDTO getById(Long id) {
        String url = serviceURLs.getStudentsUrl() + "/" + id;
        ResponseEntity<StudentDTO> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), StudentDTO.class);
        return response.getBody();
    }

    public StudentDTO getStudentWithDetails(Long id, StudentDTOFilter filter){
        StudentDTO studentDTO = getById(id);
        if(filter.isIncludeCourses()){
            String url = serviceURLs.getCourseUrl() + "/student/" + id;
            List<CourseDTO> courses = restClientUtil.exchange(url, HttpMethod.POST, restClientUtil.createHttpEntity(null), List.class).getBody();
            studentDTO.setCourses(courses);
        }
        return studentDTO;
    }

    public StudentDTO add(StudentDTO student) {
        String url = serviceURLs.getStudentsUrl();
        ResponseEntity<StudentDTO> response = restClientUtil.exchange(url, HttpMethod.POST, restClientUtil.createHttpEntity(student), StudentDTO.class);
        return response.getBody();
    }

    public StudentDTO update(Long id, StudentDTO student) {
        String url = serviceURLs.getStudentsUrl() + "/" + id;
        ResponseEntity<StudentDTO> response = restClientUtil.exchange(url, HttpMethod.PUT, restClientUtil.createHttpEntity(student), StudentDTO.class);
        return response.getBody();
    }

    public ResponseEntity<?> deleteById(Long id) {
        String url = serviceURLs.getStudentsUrl() + "/" + id;
        return restClientUtil.exchange(url, HttpMethod.DELETE, restClientUtil.createHttpEntity(null), StudentDTO.class);
    }

}

