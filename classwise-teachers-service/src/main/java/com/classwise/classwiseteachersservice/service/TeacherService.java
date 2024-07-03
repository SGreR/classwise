package com.classwise.classwiseteachersservice.service;

import com.classwise.classwiseteachersservice.model.Teacher;
import com.classwise.classwiseteachersservice.repository.TeacherRepository;
import com.classwise.classwiseteachersservice.util.MessageBuilderUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public TeacherService(TeacherRepository teacherRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.teacherRepository = teacherRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElseThrow();
    }

    public void addTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public void updateTeacher(Long id, Teacher newTeacher) {
        Teacher oldTeacher = teacherRepository.findById(id).orElseThrow();
        newTeacher.setTeacherId(oldTeacher.getTeacherId());
        teacherRepository.save(newTeacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.findById(id).orElseThrow();
        try{
            teacherRepository.deleteById(id);
            Message message = MessageBuilderUtil.buildMessage("teacher-events", "teacher-deleted", id);
            kafkaTemplate.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

