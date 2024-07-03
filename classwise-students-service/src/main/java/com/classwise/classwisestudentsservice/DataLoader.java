package com.classwise.classwisestudentsservice;

import com.classwise.classwisestudentsservice.model.Student;
import com.classwise.classwisestudentsservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) {

        Student student = new Student();
        student.setStudentName("Pedro");
        student.setCourseIds(setOf(1L, 2L, 3L));

        studentRepository.save(student);

        Student student2 = new Student();
        student2.setStudentName("Anna");
        student2.setCourseIds(setOf(1L, 2L, 3L));

        studentRepository.save(student2);
    }
}
