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
        student.setCourseIds(setOf(1L));

        studentRepository.save(student);

        Student student2 = new Student();
        student2.setStudentName("Anna");
        student2.setCourseIds(setOf(1L));

        studentRepository.save(student2);

        Student student3 = new Student();
        student3.setStudentName("Felipe");
        student3.setCourseIds(setOf(1L));

        studentRepository.save(student3);

        Student student4 = new Student();
        student4.setStudentName("João");
        student4.setCourseIds(setOf(2L));

        studentRepository.save(student4);

        Student student5 = new Student();
        student5.setStudentName("Fernanda");
        student5.setCourseIds(setOf(2L));

        studentRepository.save(student5);

        Student student6 = new Student();
        student6.setStudentName("Amanda");
        student6.setCourseIds(setOf(2L));

        studentRepository.save(student6);

        Student student7 = new Student();
        student7.setStudentName("Arthur");
        student7.setCourseIds(setOf(3L));

        studentRepository.save(student7);

        Student student8 = new Student();
        student8.setStudentName("Eduarda");
        student8.setCourseIds(setOf(3L));

        studentRepository.save(student8);
    }
}
