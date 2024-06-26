package com.classwise.classwiseteachersservice.repository;

import com.classwise.classwiseteachersservice.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
