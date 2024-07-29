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

import java.util.Collections;
import java.util.List;

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
        return response.getBody();
    }

    public List<GradesDTO> getAllWithFilters(GradesDTOFilter filters) {
        String url = restClientUtil.buildUrlWithFilters(serviceURLs.getGradesUrl(), filters);
        ResponseEntity<List<GradesDTO>> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>(){});
        List<GradesDTO> grades = response.getBody();
        if (grades != null) {
            if(filters.isIncludeCourse()){
                includeCourses(grades);
            }
            if(filters.isIncludeStudent()){
                includeStudents(grades);
            }
        }
        return filterGrades(grades, filters);
    }

    public GradesDTO getById(Long id) {
        String url = serviceURLs.getGradesUrl() + "/" + id;
        ResponseEntity<GradesDTO> gradesResponse = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), GradesDTO.class);
        GradesDTO grades = gradesResponse.getBody();
        includeStudents(Collections.singletonList(grades));
        includeCourses(Collections.singletonList(grades));

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

    private void includeStudents(List<GradesDTO> grades) {
        for (GradesDTO grade : grades) {
            if(grade.getStudentId() != null){
                String studentsUrl = serviceURLs.getStudentsUrl() + "/" + grade.getStudentId();
                ResponseEntity<StudentDTO> response = restClientUtil.exchange(studentsUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>(){});
                StudentDTO student = response.getBody();
                if(student != null) {
                    grade.setStudent(student);
                }
            }
        }
    }

    private void includeCourses(List<GradesDTO> grades) {
        for (GradesDTO grade : grades) {
            if(grade.getCourseId() != null){
                String coursesUrl = serviceURLs.getCourseUrl() + "/" + grade.getCourseId();
                ResponseEntity<CourseDTO> courseResponse = restClientUtil.exchange(coursesUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>(){});
                CourseDTO course = courseResponse.getBody();

                if(course != null) {
                    if(course.getSemesterId() != null){
                        String semestersUrl = serviceURLs.getSemesterUrl() + "/" + course.getSemesterId();
                        ResponseEntity<SemesterDTO> semesterResponse = restClientUtil.exchange(semestersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>(){});
                        SemesterDTO semester = semesterResponse.getBody();
                        course.setSemester(semester);
                    }
                    if(course.getTeacherId() != null){
                        String teachersUrl = serviceURLs.getTeachersUrl() + "/" + course.getTeacherId();
                        ResponseEntity<TeacherDTO> teacherResponse = restClientUtil.exchange(teachersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>(){});
                        TeacherDTO teacher = teacherResponse.getBody();
                        course.setTeacher(teacher);
                    }
                    grade.setCourse(course);
                }
            }

        }
    }

    private List<GradesDTO> filterGrades(List<GradesDTO> grades, GradesDTOFilter filters){
        if(filters.getByStudent().isPresent()){
            String studentName = filters.getByStudent().get();
            grades = grades.stream().filter(grade -> grade.getStudent() != null && grade.getStudent().getStudentName().contains(studentName)).toList();
        }
        if(filters.getByCourse().isPresent()){
            String courseName = filters.getByCourse().get();
            grades = grades.stream().filter(grade -> grade.getCourse() != null && grade.getCourse().getCourseName().contains(courseName)).toList();
        }
        if(filters.getBySemester().isPresent()){
            Integer semesterNumber = filters.getBySemester().get();
            grades = grades.stream().filter(grade -> grade.getCourse() != null && grade.getCourse().getSemester() != null && grade.getCourse().getSemester().getSemesterNumber() == semesterNumber).toList();
        }
        if(filters.getByYear().isPresent()){
            Integer schoolYear = filters.getByYear().get();
            grades = grades.stream().filter(grade -> grade.getCourse() != null && grade.getCourse().getSemester() != null && grade.getCourse().getSemester().getSchoolYear() == schoolYear).toList();
        }
        if(filters.getByTeacher().isPresent()){
            String teacherName = filters.getByTeacher().get();
            grades = grades.stream().filter(grade -> grade.getCourse() != null && grade.getCourse().getTeacher() != null && grade.getCourse().getTeacher().getTeacherName().contains(teacherName)).toList();
        }
        return grades;
    }
}
