package com.classwise.classwisecourseservices.repository;
import com.classwise.classwisecourseservices.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(
            "SELECT c FROM Course c " +
            "WHERE (:byName IS NULL OR LOWER(c.courseName) LIKE LOWER(CONCAT('%', :byName, '%')))")
    List<Course> findCoursesWithFilters(
            @Param("byName") String byName
    );
}
