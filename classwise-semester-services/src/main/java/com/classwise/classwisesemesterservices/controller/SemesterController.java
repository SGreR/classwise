package com.classwise.classwisesemesterservices.controller;

import com.classwise.classwisesemesterservices.model.Semester;
import com.classwise.classwisesemesterservices.service.SemesterService;
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
@RequestMapping("/internal/semesters")
public class SemesterController {
    
    private final SemesterService semesterService;
    private final ObjectMapper objectMapper;

    public SemesterController(SemesterService semesterService, ObjectMapper objectMapper) {
        this.semesterService = semesterService;
        this.objectMapper = objectMapper;
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
    public ResponseEntity<List<Semester>> getAllSemesters(
            @RequestParam(required = false) Integer bySchoolYear,
            @RequestParam(required = false) Integer bySemesterNumber
    ) {
        List<Semester> semesters;
        if(bySchoolYear == null && bySemesterNumber == null){
            semesters = semesterService.getAllSemesters();
        } else {
            semesters = semesterService.getSemestersWithFilters(bySchoolYear, bySemesterNumber);
        }
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
            JsonNode jsonNode = objectMapper.readTree(string);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.remove("courses");

                Semester semester = objectMapper.readValue(string, Semester.class);
                semesterService.addSemester(semester);
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add course: " + e.getMessage(), e);
        }
    }

    public void updateSemester(String string) {
        try {
            JsonNode jsonNode = objectMapper.readTree(string);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.remove("courses");

                Semester semester = objectMapper.readValue(string, Semester.class);
                semesterService.updateSemester(semester.getSemesterId(), semester);
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add course: " + e.getMessage(), e);
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
