package com.classwise.classwiseclassroomservices.repository;
import com.classwise.classwiseclassroomservices.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
