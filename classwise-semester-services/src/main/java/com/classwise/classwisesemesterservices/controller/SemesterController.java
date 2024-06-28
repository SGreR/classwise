package com.classwise.classwisesemesterservices.controller;

import com.classwise.classwisesemesterservices.model.Semester;
import com.classwise.classwisesemesterservices.service.SemesterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> addSemester(@RequestBody Semester semester) {
        semesterService.addSemester(semester);
        Map<String, String> message = Map.of("Message", "Criado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSemester(@PathVariable Long id, @RequestBody Semester newSemester) {
        try {
            semesterService.updateSemester(id, newSemester);
            Map<String, String> message = Map.of("Message", "Atualizado com sucesso");
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSemester(@PathVariable Long id) {
        try {
            semesterService.deleteSemester(id);
            Map<String, String> message = Map.of("Message", "Deletado com sucesso");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
        } catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}
