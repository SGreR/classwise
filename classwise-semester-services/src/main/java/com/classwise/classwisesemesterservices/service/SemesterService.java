package com.classwise.classwisesemesterservices.service;

import com.classwise.classwisesemesterservices.model.Semester;
import com.classwise.classwisesemesterservices.repository.SemesterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterService {
    
    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll();
    }

    public Semester getSemesterById(Long id) {
        return semesterRepository.findById(id).orElseThrow();
    }

    public Semester addSemester(Semester semester) {
        return semesterRepository.save(semester);
    }

    public Semester updateSemester(Long id, Semester newSemester) {
        Semester oldSemester = semesterRepository.findById(id).orElseThrow();
        newSemester.setSemesterId(oldSemester.getSemesterId());
        return semesterRepository.save(newSemester);
    }

    public void deleteSemester(Long id) {
        semesterRepository.findById(id).orElseThrow();
        semesterRepository.deleteById(id);
    }
}
