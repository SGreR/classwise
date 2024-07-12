package com.classwise.classwisegradesservice.service;

import com.classwise.classwisegradesservice.model.Grades;
import com.classwise.classwisegradesservice.repository.GradesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GradesService {

    private final GradesRepository gradesRepository;
    private final ObjectMapper objectMapper;

    public GradesService(GradesRepository gradesRepository, ObjectMapper objectMapper) {
        this.gradesRepository = gradesRepository;
        this.objectMapper = objectMapper;
    }

    public List<Grades> getAllGrades() {
        return gradesRepository.findAll();
    }

    public Grades getGradesById(Long id) {
        return gradesRepository.findById(id).orElseThrow();
    }

    public List<Grades> getGradesByCourseId(Long courseId) {
        return gradesRepository.findAll().stream().filter(grades -> grades.getCourseId() != null && grades.getCourseId().equals(courseId)).toList();
    }

    public List<Grades> getGradesByStudentId(Long studentId) {
        return gradesRepository.findAll().stream().filter(grades -> grades.getStudentId().equals(studentId)).toList();
    }

    public void addGrades(Grades grades) {
        gradesRepository.save(grades);
    }

    public void updateGrades(Long id, Grades newGrades) {
        Grades oldGrades = gradesRepository.findById(id).orElseThrow();
        newGrades.setGradesId(oldGrades.getGradesId());
        gradesRepository.save(newGrades);
    }

    public void deleteGrades(Long id) {
        gradesRepository.findById(id).orElseThrow();
        gradesRepository.deleteById(id);
    }

    public void deleteGradesByCourseId(String string) {
        try {
            Long id = objectMapper.readValue(string, Long.class);
            getGradesByCourseId(id).forEach(grades -> gradesRepository.deleteById(grades.getGradesId()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGradesByStudentId(String string) {
        try {
            Long id = objectMapper.readValue(string, Long.class);
            getGradesByStudentId(id).forEach(grades -> gradesRepository.deleteById(grades.getGradesId()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
