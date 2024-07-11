package com.classwise.classwisegradesservice.controller;

import com.classwise.classwisegradesservice.model.Grades;
import com.classwise.classwisegradesservice.service.GradesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public GradesController(GradesService gradesService) {
        this.gradesService = gradesService;
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

    public void addGrades(String string) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Grades grades = mapper.readValue(string, Grades.class);
            gradesService.addGrades(grades);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGrades(String string) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Grades grades = mapper.readValue(string, Grades.class);
            gradesService.updateGrades(grades.getGradesId(), grades);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
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
