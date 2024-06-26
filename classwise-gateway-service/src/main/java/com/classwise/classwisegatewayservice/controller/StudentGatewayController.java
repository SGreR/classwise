package com.classwise.classwisegatewayservice.controller;

import com.classwise.classwisegatewayservice.model.StudentDTO;
import com.classwise.classwisegatewayservice.payload.MessagePayload;
import com.classwise.classwisegatewayservice.service.StudentGatewayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classwise/students")
public class StudentGatewayController {

    private final StudentGatewayService studentGatewayService;

    public StudentGatewayController(StudentGatewayService studentGatewayService) {
        this.studentGatewayService = studentGatewayService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> students = studentGatewayService.getAllStudents();
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id){
        try{
            StudentDTO student = studentGatewayService.getStudentById(id);
            return ResponseEntity.ok(student);
        } catch (Exception e){
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

    }

    @PostMapping
    public ResponseEntity<MessagePayload> createStudent(@RequestBody StudentDTO student){
        studentGatewayService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessagePayload("Criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessagePayload> updateStudent(@PathVariable Long id, @RequestBody StudentDTO student){
        try{
            studentGatewayService.updateStudent(id, student);
            return ResponseEntity.ok(new MessagePayload("Atualizado com sucesso"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessagePayload> deleteStudent(@PathVariable Long id){
        try{
            studentGatewayService.deleteStudentById(id);
            return ResponseEntity.ok(new MessagePayload("Deletado com sucesso"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }
}
