package com.classwise.classwisestudentsservice.controller;

import com.classwise.classwisestudentsservice.model.Student;
import com.classwise.classwisestudentsservice.service.StudentService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/internal/students")
public class StudentController {

    private final StudentService studentService;
    private final ObjectMapper objectMapper;

    public StudentController(StudentService studentService, ObjectMapper objectMapper) {
        this.studentService = studentService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "student-events",
            groupId = "students-group",
            containerFactory = "studentListener")
    public void
    handleStudentEvents(@Payload String payload, @Header("event-type") String eventType)
    {
        if("create-student".equals(eventType)){
            addStudent(payload);
        }
        else if("update-student".equals(eventType)){
            updateStudent(payload);
        }
        else if("delete-student".equals(eventType)){
            deleteStudent(payload);
        }
    }

    @KafkaListener(topics = "course-events",
            groupId = "students-group",
            containerFactory = "studentListener")
    public void
    handleCourseEvents(@Payload String payload, @Header("event-type") String eventType)
    {
        if("course-deleted".equals(eventType)){
            studentService.removeCourseFromStudents(payload);
        }
        else if("course-updated".equals(eventType)){
            studentService.matchCoursesAndStudents(payload);
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<Student>> getStudentsByIds(@RequestBody List<Long> ids) {
        List<Student> students = studentService.getStudentsByIds(ids);
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
        }
        return ResponseEntity.ok(students);
    }

    public void addStudent(String string) {
        try {
            JsonNode jsonNode = objectMapper.readTree(string);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.remove("courses");

                Student student = objectMapper.treeToValue(objectNode, Student.class);
                studentService.addStudent(student);
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add course: " + e.getMessage(), e);
        }
    }

    public void updateStudent(String string) {
        try {
            JsonNode jsonNode = objectMapper.readTree(string);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.remove("courses");

                Student student = objectMapper.treeToValue(objectNode, Student.class);
                studentService.updateStudent(student.getStudentId(), student);
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add course: " + e.getMessage(), e);
        }
    }

    public void deleteStudent(String string) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Long id = mapper.readValue(string, Long.class);
            studentService.deleteStudent(id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
