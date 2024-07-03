package com.classwise.classwisesemesterservices.controller;

import com.classwise.classwisesemesterservices.model.Semester;
import com.classwise.classwisesemesterservices.service.SemesterService;
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
@RequestMapping("/internal/semesters")
public class SemesterController {
    
    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @KafkaListener(topics = "semester-events",
            groupId = "semesters-group",
            containerFactory = "semesterListener")
    public void
    handleSemesterEvents(@Payload String payload, @Header("event-type") String eventType)
    {
        if("create-semester".equals(eventType)){
            addSemester(payload);
        }
        else if("update-semester".equals(eventType)){
            updateSemester(payload);
        }
        else if("delete-semester".equals(eventType)){
            deleteSemester(payload);
        }
    }

    @GetMapping
    public ResponseEntity<List<Semester>> getAllSemesters() {
        List<Semester> semesters = semesterService.getAllSemesters();
        if (semesters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(semesters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSemesterById(@PathVariable Long id) {
        try {
            Semester semester = semesterService.getSemesterById(id);
            return ResponseEntity.ok(semester);
        } catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    public void addSemester(String string) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Semester semester = mapper.readValue(string, Semester.class);
            semesterService.addSemester(semester);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSemester(String string) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Semester semester = mapper.readValue(string, Semester.class);
            semesterService.updateSemester(semester.getSemesterId(), semester);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSemester(String string) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Long id = mapper.readValue(string, Long.class);
            semesterService.deleteSemester(id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
