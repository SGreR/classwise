package com.classwise.classwisegatewayservice.controller;

import com.classwise.classwisegatewayservice.filters.GradesDTOFilter;
import com.classwise.classwisegatewayservice.model.GradesDTO;
import com.classwise.classwisegatewayservice.payload.MessagePayload;
import com.classwise.classwisegatewayservice.service.GradesGatewayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/classwise/grades")
public class GradesGatewayController {

    private final GradesGatewayService gradesGatewayService;

    public GradesGatewayController(GradesGatewayService gradesGatewayService) {
        this.gradesGatewayService = gradesGatewayService;
    }

    @GetMapping
    public ResponseEntity<List<GradesDTO>> getAllGradess(
            @RequestHeader(value = "Include-Student", defaultValue = "false") boolean includeStudent,
            @RequestHeader(value = "Include-Course", defaultValue = "false") boolean includeCourse
    ){
        GradesDTOFilter filter = new GradesDTOFilter();
        filter.setIncludeStudent(includeStudent);
        filter.setIncludeCourse(includeCourse);

        List<GradesDTO> grades = gradesGatewayService.getAll();

        if(filter.isIncludeCourse() || filter.isIncludeStudent()){
            List<GradesDTO> detailedGrades = new ArrayList<>();
            for(GradesDTO grade : grades){
                GradesDTO detailedGrade = gradesGatewayService.getGradesWithDetails(grade.getGradesId(), filter);
                detailedGrades.add(detailedGrade);
            }
            grades = detailedGrades;
        }
        if (grades.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGradesById(@PathVariable Long id){
        try{
            GradesDTO grades = gradesGatewayService.getById(id);
            return ResponseEntity.ok(grades);
        } catch (Exception e){
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

    }

    @PostMapping
    public ResponseEntity<MessagePayload> createGrades(@RequestBody GradesDTO grades){
        gradesGatewayService.add(grades);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessagePayload("Criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessagePayload> updateGrades(@PathVariable Long id, @RequestBody GradesDTO grades){
        try{
            gradesGatewayService.update(id, grades);
            return ResponseEntity.ok(new MessagePayload("Atualizado com sucesso"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessagePayload> deleteGrades(@PathVariable Long id){
        try{
            gradesGatewayService.deleteById(id);
            return ResponseEntity.ok(new MessagePayload("Deletado com sucesso"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }
}
