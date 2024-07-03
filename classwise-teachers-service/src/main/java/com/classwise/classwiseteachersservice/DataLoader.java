package com.classwise.classwiseteachersservice;

import com.classwise.classwiseteachersservice.model.Teacher;
import com.classwise.classwiseteachersservice.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public void run(String... args) {

        Teacher teacher = new Teacher();
        teacher.setTeacherName("Carlos");

        teacherRepository.save(teacher);

        Teacher teacher2 = new Teacher();
        teacher2.setTeacherName("Alice");

        teacherRepository.save(teacher2);

    }
}
