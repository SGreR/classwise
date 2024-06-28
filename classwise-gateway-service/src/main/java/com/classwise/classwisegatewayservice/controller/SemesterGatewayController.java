package com.classwise.classwisegatewayservice.controller;

import com.classwise.classwisegatewayservice.model.SemesterDTO;
import com.classwise.classwisegatewayservice.payload.MessagePayload;
import com.classwise.classwisegatewayservice.service.SemesterGatewayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classwise/semesters")
public class SemesterGatewayController {
    private final SemesterGatewayService semesterGatewayService;

    public SemesterGatewayController(SemesterGatewayService semesterGatewayService) {
        this.semesterGatewayService = semesterGatewayService;
    }

    @GetMapping
    public ResponseEntity<List<SemesterDTO>> getAllSemesters(){
        List<SemesterDTO> semesters = semesterGatewayService.getAll();
        if (semesters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(semesters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSemesterById(@PathVariable Long id){
        try{
            SemesterDTO semester = semesterGatewayService.getById(id);
            return ResponseEntity.ok(semester);
        } catch (Exception e){
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

    }

    @PostMapping
    public ResponseEntity<MessagePayload> createSemester(@RequestBody SemesterDTO semester){
        semesterGatewayService.add(semester);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessagePayload("Criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessagePayload> updateSemester(@PathVariable Long id, @RequestBody SemesterDTO semester){
        try{
            semesterGatewayService.update(id, semester);
            return ResponseEntity.ok(new MessagePayload("Atualizado com sucesso"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessagePayload> deleteSemester(@PathVariable Long id){
        try{
            semesterGatewayService.deleteById(id);
            return ResponseEntity.ok(new MessagePayload("Deletado com sucesso"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }
}
