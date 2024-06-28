package com.classwise.classwisesemesterservices;

import com.classwise.classwisesemesterservices.model.Semester;
import com.classwise.classwisesemesterservices.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private SemesterRepository semesterRepository;

    @Override
    public void run(String... args) throws Exception {

        Semester semester = new Semester();
        semester.setSemesterNumber(1);
        semester.setSchoolYear(2024);

        semesterRepository.save(semester);

    }
}
