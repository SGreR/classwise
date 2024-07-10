package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.filters.CourseDTOFilter;
import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.CourseDTO;
import com.classwise.classwisegatewayservice.model.SemesterDTO;
import com.classwise.classwisegatewayservice.model.StudentDTO;
import com.classwise.classwisegatewayservice.model.TeacherDTO;
import com.classwise.classwisegatewayservice.util.MessageBuilderUtil;
import com.classwise.classwisegatewayservice.util.RestClientUtil;
import com.classwise.classwisegatewayservice.util.ServiceURLs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseGatewayService implements ServiceInterface<CourseDTO> {

    private final RestClientUtil restClientUtil;
    private final ServiceURLs serviceURLs;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CourseGatewayService(RestClientUtil restClientUtil, ServiceURLs serviceURLs, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper  ) {
        this.restClientUtil = restClientUtil;
        this.serviceURLs = serviceURLs;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<CourseDTO> getAll() {
        String url = serviceURLs.getCourseUrl();
        ResponseEntity<List<CourseDTO>> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public CourseDTO getById(Long id) {
        String url = serviceURLs.getCourseUrl() + "/" + id;
        ResponseEntity<CourseDTO> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), CourseDTO.class);
        return response.getBody();
    }

    public CourseDTO getCourseWithDetails(Long id, CourseDTOFilter filter){
        CourseDTO course = getById(id);
        if(filter.isIncludeStudents()){
            String studentsUrl = serviceURLs.getStudentsUrl() + "/multiple";
            ResponseEntity<Set<StudentDTO>> response = restClientUtil.exchange(studentsUrl, HttpMethod.POST, restClientUtil.createHttpEntity(course.getStudentIds()), new ParameterizedTypeReference<>(){});
            if(response.getBody() == null){
                course.setStudents(new HashSet<>());
            } else {
                course.setStudents(response.getBody());
            }
        }
        if(filter.isIncludeTeacher() && course.getTeacherId() != null){
            String teachersURL = serviceURLs.getTeachersUrl() + "/" + course.getTeacherId();
            ResponseEntity<TeacherDTO> response = restClientUtil.exchange(teachersURL, HttpMethod.GET, restClientUtil.createHttpEntity(null), TeacherDTO.class);
            course.setTeacher(response.getBody());
        }
        if(filter.isIncludeSemester() && course.getSemesterId() != null){
            String semestersURL = serviceURLs.getSemesterUrl() + "/" + course.getSemesterId();
            ResponseEntity<SemesterDTO> response = restClientUtil.exchange(semestersURL, HttpMethod.GET, restClientUtil.createHttpEntity(null), SemesterDTO.class);
            course.setSemester(response.getBody());
        }
        return course;
    }

    public CourseDTO add(CourseDTO course) {
        Message message = MessageBuilderUtil.buildMessage("course-events", "create-course", course);
        kafkaTemplate.send(message);
        return course;
    }

    @Override
    public CourseDTO update(Long id, CourseDTO course) {
        course.setCourseId(id);
        Message message = MessageBuilderUtil.buildMessage("course-events", "update-course", course);
        kafkaTemplate.send(message);
        return course;
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        Message message = MessageBuilderUtil.buildMessage("course-events", "delete-course", id);
        kafkaTemplate.send(message);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
