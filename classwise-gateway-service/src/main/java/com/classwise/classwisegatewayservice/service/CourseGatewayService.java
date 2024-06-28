package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.filters.CourseDTOFilter;
import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.CourseDTO;
import com.classwise.classwisegatewayservice.model.SemesterDTO;
import com.classwise.classwisegatewayservice.model.StudentDTO;
import com.classwise.classwisegatewayservice.model.TeacherDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CourseGatewayService implements ServiceInterface<CourseDTO> {

    private final RestTemplate restTemplate;
    private final StudentGatewayService studentGatewayService;
    private final TeacherGatewayService teacherGatewayService;
    private final SemesterGatewayService semesterGatewayService;

    public CourseGatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.studentGatewayService = new StudentGatewayService(restTemplate);
        this.teacherGatewayService = new TeacherGatewayService(restTemplate);
        this.semesterGatewayService = new SemesterGatewayService(restTemplate);
    }

    @Override
    public List<CourseDTO> getAll() {
        String url = "http://localhost:8081/internal/courses";
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
    public CourseDTO getById(Long id) {
        String url = "http://localhost:8081/internal/courses/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<CourseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                CourseDTO.class
        );

        return response.getBody();
    }

    public CourseDTO getCourseWithDetails(Long id, CourseDTOFilter filter){
        CourseDTO course = getById(id);
        if(filter.isIncludeStudents()){
            List<StudentDTO> students = studentGatewayService.getMultipleByIds(course.getStudentIds());
            course.setStudents(students);
        }
        if(filter.isIncludeTeacher()){
            TeacherDTO teacher = teacherGatewayService.getById(course.getTeacherId());
            course.setTeacher(teacher);
        }
        if(filter.isIncludeSemester()){
            SemesterDTO semester = semesterGatewayService.getById(course.getSemesterId());
            course.setSemester(semester);
        }

        return course;
    }

    @Override
    public CourseDTO add(CourseDTO course) {
        String url = "http://localhost:8081/internal/courses";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CourseDTO> entity = new HttpEntity<>(course, headers);

        ResponseEntity<CourseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                CourseDTO.class
        );

        return response.getBody();
    }

    @Override
    public CourseDTO update(Long id, CourseDTO course) {
        String url = "http://localhost:8081/internal/courses/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CourseDTO> entity = new HttpEntity<>(course, headers);

        ResponseEntity<CourseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                CourseDTO.class
        );

        return response.getBody();
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        String url = "http://localhost:8081/internal/courses/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CourseDTO> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                entity,
                CourseDTO.class
        );
    }
}
