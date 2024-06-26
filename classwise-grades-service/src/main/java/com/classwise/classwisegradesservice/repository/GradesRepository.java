package com.classwise.classwisegradesservice.repository;

import com.classwise.classwisegradesservice.model.Grades;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradesRepository extends JpaRepository<Grades, Long> {
}
