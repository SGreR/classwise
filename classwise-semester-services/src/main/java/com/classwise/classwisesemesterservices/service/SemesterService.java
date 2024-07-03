package com.classwise.classwisesemesterservices.service;

import com.classwise.classwisesemesterservices.model.Semester;
import com.classwise.classwisesemesterservices.repository.SemesterRepository;
import com.classwise.classwisesemesterservices.util.MessageBuilderUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterService {
    
    private final SemesterRepository semesterRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public SemesterService(SemesterRepository semesterRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.semesterRepository = semesterRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll();
    }

    public Semester getSemesterById(Long id) {
        return semesterRepository.findById(id).orElseThrow();
    }

    public void addSemester(Semester semester) {
        semesterRepository.save(semester);
    }

    public void updateSemester(Long id, Semester newSemester) {
        Semester oldSemester = semesterRepository.findById(id).orElseThrow();
        newSemester.setSemesterId(oldSemester.getSemesterId());
        semesterRepository.save(newSemester);
    }

    public void deleteSemester(Long id) {
        semesterRepository.findById(id).orElseThrow();
        try{
            semesterRepository.deleteById(id);
            Message message = MessageBuilderUtil.buildMessage("semester-events", "semester-deleted", id);
            kafkaTemplate.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
