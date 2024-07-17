package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.filters.CourseDTOFilter;
import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.*;
import com.classwise.classwisegatewayservice.util.MessageBuilderUtil;
import com.classwise.classwisegatewayservice.util.RestClientUtil;
import com.classwise.classwisegatewayservice.util.ServiceURLs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<CourseDTO> getAllWithFilters(CourseDTOFilter filters){
        String url = restClientUtil.buildUrlWithFilters(serviceURLs.getCourseUrl(), filters);
        ResponseEntity<List<CourseDTO>> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>() {});
        List<CourseDTO> courses = response.getBody();
        if(courses != null){
            if(filters.isIncludeSemester()){
                includeSemester(courses);
            }
            if(filters.isIncludeTeacher()){
                includeTeacher(courses);
            }
            if(filters.isIncludeStudents()){
                includeStudents(courses);
            }
            if(filters.isIncludeGrades()){
                includeGrades(courses);
            }
        }
        return filterCourses(courses, filters);
    }

    public CourseDTO getById(Long id) {
        String url = serviceURLs.getCourseUrl() + "/" + id;
        ResponseEntity<CourseDTO> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), CourseDTO.class);
        return response.getBody();
    }

    public CourseDTO getCourseWithDetails(Long id, CourseDTOFilter filter){
        CourseDTO course = getById(id);
        if(filter.isIncludeStudents()){
            includeStudents(Collections.singletonList(course));
        }
        if(filter.isIncludeTeacher()){
            includeTeacher(Collections.singletonList(course));
        }
        if(filter.isIncludeSemester()){
            includeSemester(Collections.singletonList(course));
        }
        if(filter.isIncludeGrades()){
            includeGrades(Collections.singletonList(course));
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

    private void includeSemester(List<CourseDTO> courses) {
        for (CourseDTO course : courses) {
            if(course.getSemesterId() != null){
                String semestersUrl = serviceURLs.getSemesterUrl() + "/" + course.getSemesterId();
                ResponseEntity<SemesterDTO> semesterResponse = restClientUtil.exchange(semestersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>() {});
                SemesterDTO semester = semesterResponse.getBody();
                course.setSemester(semester);
            }
        }
    }
    private void includeTeacher(List<CourseDTO> courses) {
        for (CourseDTO course : courses) {
            if(course.getTeacherId() != null){
                String teachersUrl = serviceURLs.getTeachersUrl() + "/" + course.getTeacherId();
                ResponseEntity<TeacherDTO> semesterResponse = restClientUtil.exchange(teachersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>() {});
                TeacherDTO teacher = semesterResponse.getBody();
                course.setTeacher(teacher);
            }
        }
    }
    private void includeStudents(List<CourseDTO> courses) {
        for (CourseDTO course : courses) {
            String studentsUrl = serviceURLs.getStudentsUrl() + "/multiple";
            ResponseEntity<Set<StudentDTO>> response = restClientUtil.exchange(studentsUrl, HttpMethod.POST, restClientUtil.createHttpEntity(course.getStudentIds()), new ParameterizedTypeReference<>(){});
            Set<StudentDTO> students = response.getBody();
            if(students == null){
                course.setStudents(new HashSet<>());
            } else {
                course.setStudents(response.getBody());
            }
        }
    }
    private void includeGrades(List<CourseDTO> courses) {
        for (CourseDTO course : courses) {
            String gradesURL = serviceURLs.getGradesUrl() + "/course/" + course.getCourseId();
            ResponseEntity<Set<GradesDTO>> response = restClientUtil.exchange(gradesURL, HttpMethod.POST, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>(){});
            Set<GradesDTO> grades = response.getBody();
            if(grades == null) {
                course.setGrades(new HashSet<>());
            } else {
                for (GradesDTO grade : grades) {
                    CourseDTO courseDTO = new CourseDTO();
                    courseDTO.setCourseName(course.getCourseName());
                    if(course.getSemester() != null){
                        courseDTO.setSemester(course.getSemester());
                    }
                    Optional<StudentDTO> optStudent = course.getStudents().stream()
                            .filter(studentDTO -> Objects.equals(studentDTO.getStudentId(), grade.getStudentId()))
                            .findFirst();

                    optStudent.ifPresent(grade::setStudent);
                    grade.setCourse(courseDTO);
                }
                course.setGrades(grades);
            }
        }
    }

    private List<CourseDTO> filterCourses(List<CourseDTO> courses, CourseDTOFilter filters) {
        if(filters.getBySemester().isPresent()){
            Integer semesterNumber = filters.getBySemester().get();
            courses = courses.stream().filter(course -> course.getSemester() != null && course.getSemester().getSemesterNumber() == semesterNumber).toList();
        }
        if(filters.getByTeacher().isPresent()){
            String teacherName = filters.getByTeacher().get();
            courses = courses.stream().filter(course -> course.getTeacher() != null && course.getTeacher().getTeacherName().contains(teacherName)).toList();
        }
        if(filters.getByYear().isPresent()){
            Integer year = filters.getByYear().get();
            courses = courses.stream().filter(course -> course.getSemester() != null && course.getSemester().getSchoolYear() == year).toList();
        }
        return courses;
    }
}
