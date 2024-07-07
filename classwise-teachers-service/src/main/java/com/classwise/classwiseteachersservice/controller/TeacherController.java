package com.classwise.classwiseteachersservice.controller;

import com.classwise.classwiseteachersservice.model.Teacher;
import com.classwise.classwiseteachersservice.service.TeacherService;
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
@RequestMapping("/internal/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final ObjectMapper objectMapper;



    public TeacherController(TeacherService teacherService, ObjectMapper objectMapper) {
        this.teacherService = teacherService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "teacher-events",
            groupId = "teachers-group",
            containerFactory = "teacherListener")
    public void
    handleTeacherEvents(@Payload String payload, @Header("event-type") String eventType)
    {
        if("create-teacher".equals(eventType)){
            addTeacher(payload);
        }
        else if("update-teacher".equals(eventType)){
            updateTeacher(payload);
        }
        else if("delete-teacher".equals(eventType)){
            deleteTeacher(payload);
        }
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        if (teachers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id) {
        try {
            Teacher teacher = teacherService.getTeacherById(id);
            return ResponseEntity.ok(teacher);
        } catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    public void addTeacher(String string) {
        try {
            JsonNode jsonNode = objectMapper.readTree(string);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.remove("courses");

                Teacher teacher = objectMapper.readValue(string, Teacher.class);
                teacherService.addTeacher(teacher);
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add course: " + e.getMessage(), e);
        }
    }

    public void updateTeacher(String string) {
        try {
            JsonNode jsonNode = objectMapper.readTree(string);
            if (jsonNode.isObject()) {
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.remove("courses");

                Teacher teacher = objectMapper.readValue(string, Teacher.class);
                teacherService.updateTeacher(teacher.getTeacherId(), teacher);
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add course: " + e.getMessage(), e);
        }
    }

    public void deleteTeacher(String string) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Long id = mapper.readValue(string, Long.class);
            teacherService.deleteTeacher(id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

