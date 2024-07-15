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
    public void run(String... args) {

        createSemester(1, 2024);
        createSemester(2, 2024);
        createSemester(1, 2023);
        createSemester(2, 2023);
        createSemester(1, 2022);
        createSemester(2, 2022);
        createSemester(1, 2021);
        createSemester(2, 2021);
    }

    private void createSemester(int semesterNumber, int schoolYear) {
        Semester semester = new Semester();
        semester.setSemesterNumber(semesterNumber);
        semester.setSchoolYear(schoolYear);
        semesterRepository.save(semester);
    }
}
