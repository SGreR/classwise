package com.classwise.classwisecourseservices.service;

import com.classwise.classwisecourseservices.model.Course;
import com.classwise.classwisecourseservices.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course newCourse) {
        Course oldCourse = courseRepository.findById(id).orElseThrow();
        newCourse.setCourseId(oldCourse.getCourseId());
        return courseRepository.save(newCourse);
    }

    public void deleteCourse(Long id) {
        courseRepository.findById(id).orElseThrow();
        courseRepository.deleteById(id);
    }
}
