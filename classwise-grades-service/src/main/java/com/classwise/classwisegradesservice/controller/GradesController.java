package com.classwise.classwisegradesservice.controller;

import com.classwise.classwisegradesservice.model.Grades;
import com.classwise.classwisegradesservice.service.GradesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> addGrades(@RequestBody Grades grades) {
        gradesService.addGrades(grades);
        Map<String, String> message = Map.of("Message", "Criado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGrades(@PathVariable Long id, @RequestBody Grades newGrades) {
        try {
            gradesService.updateGrades(id, newGrades);
            Map<String, String> message = Map.of("Message", "Atualizado com sucesso");
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGrades(@PathVariable Long id) {
        try {
            gradesService.deleteGrades(id);
            Map<String, String> message = Map.of("Message", "Deletado com sucesso");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
        } catch (Exception e) {
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}
