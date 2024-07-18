package com.classwise.classwisegradesservice.controller;

import com.classwise.classwisegradesservice.model.Grades;
import com.classwise.classwisegradesservice.service.GradesService;
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
@RequestMapping("/internal/grades")
public class GradesController {

    private final GradesService gradesService;
    private final ObjectMapper objectMapper;

    public GradesController(GradesService gradesService, ObjectMapper objectMapper) {
        this.gradesService = gradesService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "grades-events",
            groupId = "grades-group",
            containerFactory = "gradesListener")
    public void
    handleGradesEvents(@Payload String payload, @Header("event-type") String eventType)
    {
        if("create-grades".equals(eventType)){
            addGrades(payload);
        }
        else if("update-grades".equals(eventType)){
            updateGrades(payload);
        }
        else if("delete-grades".equals(eventType)){
            deleteGrades(payload);
        }
    }

    @KafkaListener(topics = "course-events",
            groupId = "grades-group",
            containerFactory = "gradesListener")
    public void
    handleCourseEvents(@Payload String payload, @Header("event-type") String eventType)
    {
        if("course-deleted".equals(eventType)){
            gradesService.deleteGradesByCourseId(payload);
        }
    }

    @KafkaListener(topics = "student-events",
            groupId = "grades-group",
            containerFactory = "gradesListener")
    public void
    handleStudentEvents(@Payload String payload, @Header("event-type") String eventType)
    {
        if("student-deleted".equals(eventType)){
            gradesService.deleteGradesByStudentId(payload);
        }
    }

    @GetMapping
    public ResponseEntity<List<Grades>> getAllGrades() {
        List<Grades> grades = gradesService.getAllGrades();
        if (grades.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGradesById(@PathVariable Long id) {
        try {
            Grades grades = gradesService.getGradesById(id);
            return ResponseEntity.ok(grades);
        } catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PostMapping("/student/{id}")
    public ResponseEntity<?> getGradesByStudentId(@PathVariable Long id) {
        try{
            List<Grades> grades = gradesService.getGradesByStudentId(id);
            return ResponseEntity.ok(grades);
        }catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @PostMapping("/course/{id}")
    public ResponseEntity<?> getGradesByCourseId(@PathVariable Long id) {
        try{
            List<Grades> grades = gradesService.getGradesByCourseId(id);
            return ResponseEntity.ok(grades);
        }catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    public void addGrades(String string) {
        try {
            JsonNode jsonNode = objectMapper.readTree(string);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.remove("student");
                objectNode.remove("course");

                Grades emptyGrades = new Grades();
                Grades grades = objectMapper.treeToValue(objectNode, Grades.class);

                emptyGrades.setTestNumber(grades.getTestNumber());
                emptyGrades.setStudentId(grades.getStudentId());
                emptyGrades.setCourseId(grades.getCourseId());

                gradesService.addGrades(emptyGrades);
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add grades: " + e.getMessage(), e);
        }
    }

    public void updateGrades(String string) {
        try {
            JsonNode jsonNode = objectMapper.readTree(string);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.remove("student");
                objectNode.remove("course");

                Grades grades = objectMapper.treeToValue(objectNode, Grades.class);
                gradesService.updateGrades(grades.getGradesId(), grades);
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add grades: " + e.getMessage(), e);
        }
    }

    public void deleteGrades(String string) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Long id = mapper.readValue(string, Long.class);
            gradesService.deleteGrades(id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
