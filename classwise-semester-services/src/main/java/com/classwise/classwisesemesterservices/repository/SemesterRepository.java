package com.classwise.classwisesemesterservices.repository;

import com.classwise.classwisesemesterservices.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
}
