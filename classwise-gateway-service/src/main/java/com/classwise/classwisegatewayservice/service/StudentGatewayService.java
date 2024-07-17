package com.classwise.classwisegatewayservice.service;

import com.classwise.classwisegatewayservice.filters.StudentDTOFilter;
import com.classwise.classwisegatewayservice.interfaces.ServiceInterface;
import com.classwise.classwisegatewayservice.model.*;
import com.classwise.classwisegatewayservice.util.MessageBuilderUtil;
import com.classwise.classwisegatewayservice.util.RestClientUtil;
import com.classwise.classwisegatewayservice.util.ServiceURLs;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import java.util.*;
import java.util.stream.Collectors;

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
        ResponseEntity<List<StudentDTO>> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public List<StudentDTO> getStudentsWithFilters(StudentDTOFilter filters){
        String url = restClientUtil.buildUrlWithFilters(serviceURLs.getStudentsUrl(), filters);
        ResponseEntity<List<StudentDTO>> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>() {});
        List<StudentDTO> students = response.getBody();
        if(filters.isIncludeCourses()){
            includeCourses(students);
        }
        if(filters.isIncludeGrades()){
            includeGrades(students);
        }
        return students;
    }

    public StudentDTO getById(Long id) {
        String url = serviceURLs.getStudentsUrl() + "/" + id;
        ResponseEntity<StudentDTO> response = restClientUtil.exchange(url, HttpMethod.GET, restClientUtil.createHttpEntity(null), StudentDTO.class);
        return response.getBody();
    }

    public StudentDTO getStudentWithDetails(Long id, StudentDTOFilter filter){
        StudentDTO student = getById(id);

        if(filter.isIncludeCourses()){
            String url = serviceURLs.getCourseUrl() + "/student/" + id;
            ResponseEntity<Set<CourseDTO>> response = restClientUtil.exchange(url, HttpMethod.POST, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>() {});
            Set<CourseDTO> courses = response.getBody();

            if(courses != null) {
                for (CourseDTO course : courses) {
                    if (course.getTeacherId() != null) {
                        String teachersUrl = serviceURLs.getTeachersUrl() + "/" + course.getTeacherId();
                        TeacherDTO teacher = restClientUtil.exchange(teachersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), TeacherDTO.class).getBody();
                        course.setTeacher(teacher);
                    }
                    if(course.getSemesterId() != null){
                        String semestersUrl = serviceURLs.getSemesterUrl() + "/" + course.getSemesterId();
                        SemesterDTO semester = restClientUtil.exchange(semestersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), SemesterDTO.class).getBody();
                        course.setSemester(semester);
                    }


                }
            }
            student.setCourses(courses);
        }
        if(filter.isIncludeGrades()){
            String gradesUrl = serviceURLs.getGradesUrl() + "/student/" + id;
            ResponseEntity<Set<GradesDTO>> response = restClientUtil.exchange(gradesUrl, HttpMethod.POST, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>() {});
            Set<GradesDTO> grades = response.getBody();

            for(GradesDTO grade: grades){
                if(grade.getCourseId() != null){
                    grade.setCourse(
                            student.getCourses().stream()
                                    .filter(course -> course.getCourseId().equals(grade.getCourseId()))
                                    .findFirst()
                                    .orElse(null)
                    );
                }
            }

            List<GradesDTO> sortedGrades = grades.stream()
                    .filter(grade -> grade.getCourse() != null && grade.getCourse().getSemester() != null)
                    .collect(Collectors.groupingBy(
                            grade -> grade.getCourse().getSemester().getSchoolYear(),
                            TreeMap::new,
                            Collectors.toList()
                    ))
                    .values()
                    .stream()
                    .flatMap(list -> list.stream().sorted(Comparator.comparingInt(grade -> grade.getCourse().getSemester().getSemesterNumber())))
                    .collect(Collectors.toList());

            sortedGrades.addAll(
                    grades.stream()
                            .filter(grade -> grade.getCourse() == null || grade.getCourse().getSemester() == null)
                            .collect(Collectors.toList())
            );

            student.setGrades(new LinkedHashSet<>(sortedGrades));
        }
        return student;
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

    private void includeCourses(List<StudentDTO> students){
        StudentDTOFilter courseFilter = new StudentDTOFilter();

        for(StudentDTO student : students) {
            courseFilter.setByName(Optional.of(student.getStudentName()));
            String url = restClientUtil.buildUrlWithFilters(serviceURLs.getCourseUrl(), courseFilter);
            ResponseEntity<Set<CourseDTO>> response = restClientUtil.exchange(url, HttpMethod.POST, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>() {});
            Set<CourseDTO> courses = response.getBody();

            if (courses != null) {
                for (CourseDTO course : courses) {
                    if (course.getTeacherId() != null) {
                        String teachersUrl = serviceURLs.getTeachersUrl() + "/" + course.getTeacherId();
                        TeacherDTO teacher = restClientUtil.exchange(teachersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), TeacherDTO.class).getBody();
                        course.setTeacher(teacher);
                    }
                    if (course.getSemesterId() != null) {
                        String semestersUrl = serviceURLs.getSemesterUrl() + "/" + course.getSemesterId();
                        SemesterDTO semester = restClientUtil.exchange(semestersUrl, HttpMethod.GET, restClientUtil.createHttpEntity(null), SemesterDTO.class).getBody();
                        course.setSemester(semester);
                    }
                }
            }
            student.setCourses(courses);
        }
    }

    //Update this to a get all with filters later on
    private void includeGrades(List<StudentDTO> students){
        for(StudentDTO student : students){
            String gradesUrl = serviceURLs.getGradesUrl() + "/student/" + student.getStudentId();
            ResponseEntity<Set<GradesDTO>> response = restClientUtil.exchange(gradesUrl, HttpMethod.POST, restClientUtil.createHttpEntity(null), new ParameterizedTypeReference<>() {});
            Set<GradesDTO> grades = response.getBody();

            for(GradesDTO grade: grades){
                if(grade.getCourseId() != null){
                    grade.setCourse(
                            student.getCourses().stream()
                                    .filter(course -> course.getCourseId().equals(grade.getCourseId()))
                                    .findFirst()
                                    .orElse(null)
                    );
                }
            }

            List<GradesDTO> sortedGrades = grades.stream()
                    .filter(grade -> grade.getCourse() != null && grade.getCourse().getSemester() != null)
                    .collect(Collectors.groupingBy(
                            grade -> grade.getCourse().getSemester().getSchoolYear(),
                            TreeMap::new,
                            Collectors.toList()
                    ))
                    .values()
                    .stream()
                    .flatMap(list -> list.stream().sorted(Comparator.comparingInt(grade -> grade.getCourse().getSemester().getSemesterNumber())))
                    .collect(Collectors.toList());

            sortedGrades.addAll(
                    grades.stream()
                            .filter(grade -> grade.getCourse() == null || grade.getCourse().getSemester() == null)
                            .collect(Collectors.toList())
            );

            student.setGrades(new LinkedHashSet<>(sortedGrades));
        }
    }
}

