package com.classwise.classwisecourseservices.service;

import com.classwise.classwisecourseservices.model.Course;
import com.classwise.classwisecourseservices.repository.CourseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public List<Course> getCourseByStudentId(Long id) {
        return courseRepository.findAll().stream().filter(course -> course.getStudentIds().contains(id)).toList();
    }

    public List<Course> getCoursesByTeacherId(Long id) {
        return courseRepository.findAll().stream().filter(course -> course.getTeacherId().equals(id)).toList();
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

    public void removeTeacherFromCourses(String payload){
        ObjectMapper mapper = new ObjectMapper();
        Long id;
        try {
            id = mapper.readValue(payload, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        getCoursesByTeacherId(id).forEach(course -> {
            course.setTeacherId(null);
            updateCourse(course.getCourseId(), course);
        });
    }
}
