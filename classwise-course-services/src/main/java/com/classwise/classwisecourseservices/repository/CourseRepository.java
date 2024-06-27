package com.classwise.classwisecourseservices.repository;
import com.classwise.classwisecourseservices.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
