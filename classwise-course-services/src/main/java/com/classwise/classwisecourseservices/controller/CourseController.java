package com.classwise.classwisecourseservices.controller;

import com.classwise.classwisecourseservices.model.Course;
import com.classwise.classwisecourseservices.service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/internal/courses")
public class CourseController {

    private final CourseService courseService;
    private final ObjectMapper objectMapper;


    public CourseController(CourseService courseService, ObjectMapper objectMapper) {
        this.courseService = courseService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "course-events",
            groupId = "courses-group",
            containerFactory = "courseListener")
    public void
    handleCourseEvents(@Payload String payload, @Header("event-type") String eventType)
    {
        if("create-course".equals(eventType)){
            addCourse(payload);
        }
        else if("update-course".equals(eventType)){
            updateCourse(payload);
        }
        else if("delete-course".equals(eventType)){
            deleteCourse(payload);
        }
    }

    @KafkaListener(topics = "teacher-events",
            groupId = "courses-group",
            containerFactory = "courseListener")
    public void
    handleTeacherEvents(@Payload String payload, @Header("event-type") String eventType)
    {
        if("teacher-deleted".equals(eventType)){
            courseService.removeTeacherFromCourses(payload);
        }
    }

    @KafkaListener(topics = "student-events",
            groupId = "courses-group",
            containerFactory = "courseListener")
    public void
    handleStudentEvents(@Payload String payload, @Header("event-type") String eventType)
    {
        if("student-deleted".equals(eventType)){
            courseService.removeStudentFromCourses(payload);
        }
        else if("student-updated".equals(eventType) || "student-created".equals(eventType)){
            courseService.matchStudentsAndCourses(payload);
        }
    }

    @KafkaListener(topics = "semester-events",
            groupId = "courses-group",
            containerFactory = "courseListener")
    public void
    handleSemesterEvents(@Payload String payload, @Header("event-type") String eventType)
    {
        if("semester-deleted".equals(eventType)){
            courseService.removeSemesterFromCourses(payload);
        }
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(
            @RequestParam(required = false) String byName
    ) {
        List<Course> courses;
        if(byName != null){
            courses = courseService.getCoursesWithFilters(byName);
        } else {
            courses = courseService.getAllCourses();
        }
        if (courses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/student/{id}")
    public ResponseEntity<?> getCoursesByStudentId(@PathVariable Long id) {
        try{
            List<Course> courses = courseService.getCoursesByStudentId(id);
            return ResponseEntity.ok(courses);
        }catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PostMapping("/semester/{id}")
    public ResponseEntity<?> getCoursesBySemesterId(@PathVariable Long id) {
        try{
            List<Course> courses = courseService.getCoursesBySemesterId(id);
            return ResponseEntity.ok(courses);
        }catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PostMapping("/teacher/{id}")
    public ResponseEntity<?> getCoursesByTeacherId(@PathVariable Long id) {
        try{
            List<Course> courses = courseService.getCoursesByTeacherId(id);
            return ResponseEntity.ok(courses);
        }catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        try{
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        }catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    public void addCourse(String string) {
        try {
            JsonNode jsonNode = objectMapper.readTree(string);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.remove("semester");
                objectNode.remove("teacher");
                objectNode.remove("students");

                Course course = objectMapper.treeToValue(objectNode, Course.class);
                courseService.addCourse(course);
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add course: " + e.getMessage(), e);
        }
    }

    public void updateCourse(String string) {
        try {
            JsonNode jsonNode = objectMapper.readTree(string);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.remove("semester");
                objectNode.remove("teacher");
                objectNode.remove("students");

                Course course = objectMapper.treeToValue(objectNode, Course.class);
                courseService.updateCourse(course.getCourseId(), course);
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add course: " + e.getMessage(), e);
        }
    }

    public void deleteCourse(String string) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Long id = mapper.readValue(string, Long.class);
            courseService.deleteCourse(id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
