package com.classwise.classwisegradesservice.service;

import com.classwise.classwisegradesservice.model.Grades;
import com.classwise.classwisegradesservice.repository.GradesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GradesService {

    private final GradesRepository gradesRepository;

    public GradesService(GradesRepository gradesRepository) {
        this.gradesRepository = gradesRepository;
    }

    public List<Grades> getAllGrades() {
        return gradesRepository.findAll();
    }

    public Grades getGradesById(Long id) {
        return gradesRepository.findById(id).orElseThrow();
    }

    public Grades addGrades(Grades grades) {
        return gradesRepository.save(grades);
    }

    public Grades updateGrades(Long id, Grades newGrades) {
        Grades oldGrades = gradesRepository.findById(id).orElseThrow();
        newGrades.setGradesId(oldGrades.getGradesId());
        return gradesRepository.save(newGrades);
    }

    public void deleteGrades(Long id) {
        gradesRepository.findById(id).orElseThrow();
        gradesRepository.deleteById(id);
    }
}
