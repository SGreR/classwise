package com.classwise.classwisestudentsservice;

import com.classwise.classwisestudentsservice.model.Student;
import com.classwise.classwisestudentsservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {

        Student student = new Student();
        student.setStudentId(1L);
        student.setStudentName("Pedro");
        student.setCourseIds(listOf(1L, 2L));

        studentRepository.save(student);
    }
}
