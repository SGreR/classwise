package com.classwise.classwisestudentsservice.repository;

import com.classwise.classwisestudentsservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s " +
            "WHERE (:byName IS NULL OR LOWER(s.studentName) LIKE LOWER(CONCAT('%', :byName, '%')))")
    List<Student> findStudentsWithFilters(
            @Param("byName") String byName
    );
}
