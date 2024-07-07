package com.classwise.classwisegatewayservice.controller;

import com.classwise.classwisegatewayservice.filters.StudentDTOFilter;
import com.classwise.classwisegatewayservice.model.StudentDTO;
import com.classwise.classwisegatewayservice.payload.MessagePayload;
import com.classwise.classwisegatewayservice.service.StudentGatewayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/classwise/students")
public class StudentGatewayController {

    private final StudentGatewayService studentGatewayService;

    public StudentGatewayController(StudentGatewayService studentGatewayService) {
        this.studentGatewayService = studentGatewayService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> students = studentGatewayService.getAll();
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(
            @PathVariable Long id,
            @RequestHeader(value = "Include-Courses", defaultValue = "false") boolean includeCourses){
        try{
            StudentDTOFilter filter = new StudentDTOFilter();
            filter.setIncludeCourses(includeCourses);
            StudentDTO student;
            if(includeCourses){
                student = studentGatewayService.getStudentWithDetails(id, filter);
            } else {
                student = studentGatewayService.getById(id);
            }
            return ResponseEntity.ok(student);
        } catch (Exception e){
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

    }

    @PostMapping
    public ResponseEntity<MessagePayload> createStudent(@RequestBody StudentDTO student){
        studentGatewayService.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessagePayload("Criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessagePayload> updateStudent(@PathVariable Long id, @RequestBody StudentDTO student){
        try{
            studentGatewayService.update(id, student);
            return ResponseEntity.ok(new MessagePayload("Atualizado com sucesso"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessagePayload> deleteStudent(@PathVariable Long id){
        ResponseEntity<?> response = studentGatewayService.deleteById(id);
        if(response.getStatusCode() == HttpStatus.NO_CONTENT){
            return ResponseEntity.ok(new MessagePayload("Deletado com sucesso"));
        } else if(response.getStatusCode() == HttpStatus.NOT_FOUND){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("Objeto não encontrado"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
