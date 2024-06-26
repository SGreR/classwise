package com.classwise.classwisestudentsservice.repository;

import com.classwise.classwisestudentsservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
