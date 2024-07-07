package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.filters.GradesDTOFilter;
import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.*;
import com.classwise.classwisegatewayservice.util.MessageBuilderUtil;
import com.classwise.classwisegatewayservice.util.RestClientUtil;
import com.classwise.classwisegatewayservice.util.ServiceURLs;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GradesGatewayService implements ServiceInterface<GradesDTO> {

    private final RestClientUtil restClientUtil;
    private final ServiceURLs serviceURLs;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public GradesGatewayService(RestClientUtil restClientUtil, ServiceURLs serviceURLs, KafkaTemplate<String, String> kafkaTemplate) {
        this.restClientUtil = restClientUtil;
        this.serviceURLs = serviceURLs;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<GradesDTO> getAll() {
        String url = serviceURLs.getGradesUrl();
        ResponseEntity<List<GradesDTO>> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>(){});
        List<GradesDTO> finalResponse = response.getBody();
        return finalResponse;
    }

    public GradesDTO getById(Long id) {
        String url = serviceURLs.getGradesUrl() + "/" + id;
        ResponseEntity<GradesDTO> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), GradesDTO.class);
        return response.getBody();
    }

    public GradesDTO getGradesWithDetails(Long id, GradesDTOFilter filter){
        GradesDTO grades = getById(id);
        if(filter.isIncludeStudent()){
            String studentsUrl = serviceURLs.getStudentsUrl() + "/" + grades.getStudentId();
            StudentDTO response = restClientUtil.exchange(studentsUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), StudentDTO.class).getBody();
            grades.setStudent(response);
        }
        if(filter.isIncludeCourse()){
            String coursesUrl = serviceURLs.getCourseUrl() + "/" + grades.getCourseId();
            ResponseEntity<CourseDTO> response = restClientUtil.exchange(coursesUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), CourseDTO.class);
            CourseDTO course = response.getBody();

            String semestersUrl = serviceURLs.getSemesterUrl() + "/" + course.getSemesterId();
            ResponseEntity<SemesterDTO> semesterResponse = restClientUtil.exchange(semestersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), SemesterDTO.class);
            SemesterDTO semester = semesterResponse.getBody();

            course.setSemester(semester);
            grades.setCourse(course);
        }
        return grades;
    }

    public GradesDTO add(GradesDTO grades) {
        Message message = MessageBuilderUtil.buildMessage("grades-events", "create-grades", grades);
        kafkaTemplate.send(message);
        return grades;
    }

    public GradesDTO update(Long id, GradesDTO grades) {
        Message message = MessageBuilderUtil.buildMessage("grades-events", "update-grades", grades);
        kafkaTemplate.send(message);
        return grades;
    }

    public ResponseEntity<?> deleteById(Long id) {
        Message message = MessageBuilderUtil.buildMessage("grades-events", "delete-grades", id);
        kafkaTemplate.send(message);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
