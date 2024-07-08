package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.filters.StudentDTOFilter;
import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.CourseDTO;
import com.classwise.classwisegatewayservice.model.SemesterDTO;
import com.classwise.classwisegatewayservice.model.StudentDTO;
import com.classwise.classwisegatewayservice.model.TeacherDTO;
import com.classwise.classwisegatewayservice.util.MessageBuilderUtil;
import com.classwise.classwisegatewayservice.util.RestClientUtil;
import com.classwise.classwisegatewayservice.util.ServiceURLs;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import java.util.List;
import java.util.Set;

@Service
public class StudentGatewayService implements ServiceInterface<StudentDTO> {

    private final RestClientUtil restClientUtil;
    private final ServiceURLs serviceURLs;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public StudentGatewayService(RestClientUtil restClientUtil, ServiceURLs serviceURLs, KafkaTemplate<String, String> kafkaTemplate) {
        this.restClientUtil = restClientUtil;
        this.serviceURLs = serviceURLs;
        this.kafkaTemplate = kafkaTemplate;
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
            ResponseEntity<Set<CourseDTO>> response = restClientUtil.exchange(url, HttpMethod.POST, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>() {});
            Set<CourseDTO> courses = response.getBody();

            if(courses != null) {
                for (CourseDTO course : courses) {
                    String teachersUrl = serviceURLs.getTeachersUrl() + "/" + course.getTeacherId();
                    TeacherDTO teacher = restClientUtil.exchange(teachersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), TeacherDTO.class).getBody();

                    String semestersUrl = serviceURLs.getSemesterUrl() + "/" + course.getSemesterId();
                    SemesterDTO semester = restClientUtil.exchange(semestersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), SemesterDTO.class).getBody();

                    course.setTeacher(teacher);
                    course.setSemester(semester);
                }
            }
            studentDTO.setCourses(courses);
        }
        return studentDTO;
    }

    public StudentDTO add(StudentDTO student) {
        Message message = MessageBuilderUtil.buildMessage("student-events", "create-student", student);
        kafkaTemplate.send(message);
        return student;
    }

    public StudentDTO update(Long id, StudentDTO student) {
        Message message = MessageBuilderUtil.buildMessage("student-events", "update-student", student);
        kafkaTemplate.send(message);
        return student;
    }

    public ResponseEntity<?> deleteById(Long id) {
        Message message = MessageBuilderUtil.buildMessage("student-events", "delete-student", id);
        kafkaTemplate.send(message);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

