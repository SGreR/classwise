package com.classwise.classwisegatewayservice.controller;

import com.classwise.classwisegatewayservice.model.TeacherDTO;
import com.classwise.classwisegatewayservice.payload.MessagePayload;
import com.classwise.classwisegatewayservice.service.TeacherGatewayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classwise/teachers")
public class TeacherGatewayController {

    private final TeacherGatewayService teacherGatewayService;

    public TeacherGatewayController(TeacherGatewayService teacherGatewayService) {
        this.teacherGatewayService = teacherGatewayService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers(){
        List<TeacherDTO> teachers = teacherGatewayService.getAll();
        if (teachers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id){
        try{
            TeacherDTO teacher = teacherGatewayService.getById(id);
            return ResponseEntity.ok(teacher);
        } catch (Exception e){
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

    }

    @PostMapping
    public ResponseEntity<MessagePayload> createTeacher(@RequestBody TeacherDTO teacher){
        teacherGatewayService.add(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessagePayload("Requisição enviada com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessagePayload> updateTeacher(@PathVariable Long id, @RequestBody TeacherDTO teacher){
        try{
            teacherGatewayService.update(id, teacher);
            return ResponseEntity.ok(new MessagePayload("Requisição enviada com sucesso"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessagePayload> deleteTeacher(@PathVariable Long id){
        try{
            teacherGatewayService.deleteById(id);
            return ResponseEntity.ok(new MessagePayload("Requisição enviada com sucesso"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }
}
