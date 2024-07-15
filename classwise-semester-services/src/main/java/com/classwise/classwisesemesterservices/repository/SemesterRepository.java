package com.classwise.classwisesemesterservices.repository;

import com.classwise.classwisesemesterservices.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    @Query("SELECT s FROM Semester s " +
            "WHERE (:bySchoolYear IS NULL OR s.schoolYear = :bySchoolYear) " +
            "AND (:bySemesterNumber IS NULL OR s.semesterNumber = :bySemesterNumber)")
    List<Semester> findSemestersWithFilters(
            @Param("bySchoolYear") Integer bySchoolYear,
            @Param("bySemesterNumber") Integer bySemesterNumber);
}
