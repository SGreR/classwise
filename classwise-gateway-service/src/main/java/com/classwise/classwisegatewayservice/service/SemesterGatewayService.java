package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.CourseDTO;
import com.classwise.classwisegatewayservice.model.SemesterDTO;
import com.classwise.classwisegatewayservice.model.TeacherDTO;
import com.classwise.classwisegatewayservice.util.MessageBuilderUtil;
import com.classwise.classwisegatewayservice.util.RestClientUtil;
import com.classwise.classwisegatewayservice.util.ServiceURLs;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SemesterGatewayService implements ServiceInterface<SemesterDTO> {

    private final RestClientUtil restClientUtil;
    private final ServiceURLs serviceURLs;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public SemesterGatewayService(RestClientUtil restClientUtil, ServiceURLs serviceURLs, KafkaTemplate<String, String> kafkaTemplate) {
        this.restClientUtil = restClientUtil;
        this.serviceURLs = serviceURLs;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<SemesterDTO> getAll() {
        String url = serviceURLs.getSemesterUrl();
        ResponseEntity<List> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), List.class);
        return response.getBody();
    }

    public SemesterDTO getById(Long id) {
        String url = serviceURLs.getSemesterUrl() + "/" + id;
        ResponseEntity<SemesterDTO> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), SemesterDTO.class);
        return response.getBody();
    }

    public SemesterDTO getSemesterWithCourses(Long id) {
        String semestersUrl = serviceURLs.getSemesterUrl() + "/" + id;
        SemesterDTO semester = restClientUtil.exchange(semestersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), SemesterDTO.class).getBody();

        String coursesUrl = serviceURLs.getCourseUrl() + "/semester/" + id;
        ResponseEntity<Set<CourseDTO>> response = restClientUtil.exchange(coursesUrl, HttpMethod.POST, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>(){});
        Set<CourseDTO> courses = response.getBody();

        for (CourseDTO course : courses) {
            String teachersUrl = serviceURLs.getTeachersUrl() + "/" + course.getTeacherId();
            TeacherDTO teacher = restClientUtil.exchange(teachersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), TeacherDTO.class).getBody();
            course.setTeacher(teacher);
        }

        semester.setCourses(courses);

        return semester;

    }

    public SemesterDTO add(SemesterDTO semester) {
        Message message = MessageBuilderUtil.buildMessage("semester-events", "create-semester", semester);
        kafkaTemplate.send(message);
        return semester;
    }

    public SemesterDTO update(Long id, SemesterDTO semester) {
        Message message = MessageBuilderUtil.buildMessage("semester-events", "update-semester", semester);
        kafkaTemplate.send(message);
        return semester;
    }

    public ResponseEntity<?> deleteById(Long id) {
        Message message = MessageBuilderUtil.buildMessage("semester-events", "delete-semester", id);
        kafkaTemplate.send(message);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
