package com.classwise.classwiseteachersservice.service;

import com.classwise.classwiseteachersservice.model.Teacher;
import com.classwise.classwiseteachersservice.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElseThrow();
    }

    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(Long id, Teacher newTeacher) {
        Teacher oldTeacher = teacherRepository.findById(id).orElseThrow();
        newTeacher.setTeacherId(oldTeacher.getTeacherId());
        return teacherRepository.save(newTeacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.findById(id).orElseThrow();
        teacherRepository.deleteById(id);
    }
}

