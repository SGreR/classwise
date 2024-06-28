package com.classwise.classwisegatewayservice.controller;

import com.classwise.classwisegatewayservice.filters.CourseDTOFilter;
import com.classwise.classwisegatewayservice.model.CourseDTO;
import com.classwise.classwisegatewayservice.payload.MessagePayload;
import com.classwise.classwisegatewayservice.service.CourseGatewayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classwise/courses")
public class CourseGatewayController {

    private final CourseGatewayService courseGatewayService;

    public CourseGatewayController(CourseGatewayService courseGatewayService) {
        this.courseGatewayService = courseGatewayService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        List<CourseDTO> courses = courseGatewayService.getAll();
        if (courses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(
            @PathVariable Long id,
            @RequestHeader(value = "Include-Students", defaultValue = "false") boolean includeStudents,
            @RequestHeader(value = "Include-Teacher", defaultValue = "false") boolean includeTeacher,
            @RequestHeader(value = "Include-Semester", defaultValue = "false") boolean includeSemester){
        try{
            CourseDTOFilter filter = new CourseDTOFilter();
            filter.setIncludeStudents(includeStudents);
            filter.setIncludeTeacher(includeTeacher);
            filter.setIncludeSemester(includeSemester);
            CourseDTO course;
            if (filter.isIncludeStudents() || filter.isIncludeTeacher() || filter.isIncludeSemester()) {
                course = courseGatewayService.getCourseWithDetails(id, filter);
            } else {
                course = courseGatewayService.getById(id);
            }
            return ResponseEntity.ok(course);
        } catch (Exception e){
            Map<String, String> message = Map.of("Message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

    }

    @PostMapping
    public ResponseEntity<MessagePayload> createCourse(@RequestBody CourseDTO course){
        courseGatewayService.add(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessagePayload("Criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessagePayload> updateCourse(@PathVariable Long id, @RequestBody CourseDTO course){
        try{
            courseGatewayService.update(id, course);
            return ResponseEntity.ok(new MessagePayload("Atualizado com sucesso"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessagePayload> deleteCourse(@PathVariable Long id){
        try{
            courseGatewayService.deleteById(id);
            return ResponseEntity.ok(new MessagePayload("Deletado com sucesso"));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }
}
