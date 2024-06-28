package com.classwise.classwisestudentsservice.service;

import com.classwise.classwisestudentsservice.model.Student;
import com.classwise.classwisestudentsservice.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public List<Student> getStudentsByIds(List<Long> ids) {
        return studentRepository.findAllById(ids);
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student newStudent) {
        Student oldStudent = studentRepository.findById(id).orElseThrow();
        newStudent.setStudentId(oldStudent.getStudentId());
        return studentRepository.save(newStudent);
    }

    public void deleteStudent(Long id) {
        studentRepository.findById(id).orElseThrow();
        studentRepository.deleteById(id);
    }
}

