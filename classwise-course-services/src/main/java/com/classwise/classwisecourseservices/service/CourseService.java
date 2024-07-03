package com.classwise.classwisecourseservices.service;

import com.classwise.classwisecourseservices.model.Course;
import com.classwise.classwisecourseservices.repository.CourseRepository;
import com.classwise.classwisecourseservices.util.MessageBuilderUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CourseService(CourseRepository courseRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.courseRepository = courseRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    public List<Course> getCoursesByStudentId(Long id) {
        List<Course> matchingCourses = courseRepository.findAll().stream().filter(course -> course.getStudentIds().contains(id)).toList();
        return matchingCourses;
    }

    public List<Course> getCoursesByTeacherId(Long id) {
        return courseRepository.findAll().stream().filter(course -> course.getTeacherId().equals(id)).toList();
    }

    public List<Course> getCoursesBySemesterId(Long id) {
        return courseRepository.findAll().stream().filter(course -> course.getSemesterId().equals(id)).toList();
    }

    public void addCourse(Course course) {
        try{
            Course newCourse = courseRepository.save(course);
            Message message = MessageBuilderUtil.buildMessage("course-events", "course-created", newCourse.getCourseId());
            kafkaTemplate.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCourse(Long id, Course newCourse) {
        try {
            Course oldCourse = courseRepository.findById(id).orElseThrow();
            newCourse.setCourseId(oldCourse.getCourseId());
            courseRepository.save(newCourse);
            Message message = MessageBuilderUtil.buildMessage("course-events", "course-updated", newCourse);
            kafkaTemplate.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCourse(Long id) {
        try{
            courseRepository.findById(id).orElseThrow();
            courseRepository.deleteById(id);
            Message message = MessageBuilderUtil.buildMessage("course-events", "course-deleted", id);
            kafkaTemplate.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public void removeStudentFromCourses(String payload){
        ObjectMapper mapper = new ObjectMapper();
        Long id;
        try {
            id = mapper.readValue(payload, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        getCoursesByStudentId(id).forEach(course -> {
            Set<Long> studentIds = course.getStudentIds();
            studentIds.remove(id);
            course.setStudentIds(studentIds);
            updateCourse(course.getCourseId(), course);
        });
    }

    public void removeStudentFromCourses(Long studentId, Set<Long> courseIds) {
        courseIds.forEach(courseId -> {
            Course course = getCourseById(courseId);
            Set<Long> studentIds = course.getStudentIds();
            if (studentIds.contains(studentId)) {
                studentIds.remove(studentId);
                course.setStudentIds(studentIds);
                updateCourse(courseId, course);
            }
        });
    }

    public void addStudentToCourses(Long studentId, Set<Long> courseIds){
        courseIds.forEach(courseId -> {
            Course course = getCourseById(courseId);
            Set<Long> studentIds = course.getStudentIds();
            if (!studentIds.contains(studentId)) {
                studentIds.add(studentId);
                course.setStudentIds(studentIds);
                updateCourse(courseId, course);
            }
        });
    }

    public void matchStudentsAndCourses(String payload){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(payload);

            if (jsonNode.isObject()) {
                Long studentId = jsonNode.get("studentId").asLong();
                Set<Long> newCourseIds = mapper.convertValue(jsonNode.get("courseIds"), new TypeReference<Set<Long>>() {});
                Set<Long> newCourseSet = new HashSet<>(newCourseIds);

                Set<Long> currentCourseIds = getCoursesByStudentId(studentId).stream()
                        .map(Course::getCourseId)
                        .collect(Collectors.toSet());

                Set<Long> coursesToAdd = new HashSet<>(newCourseSet);
                coursesToAdd.removeAll(currentCourseIds);

                Set<Long> coursesToRemove = new HashSet<>(currentCourseIds);
                coursesToRemove.removeAll(newCourseSet);

                if (!coursesToAdd.isEmpty()) {
                    addStudentToCourses(studentId, coursesToAdd);
                }

                if (!coursesToRemove.isEmpty()) {
                    removeStudentFromCourses(studentId, coursesToRemove);
                }
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to match courses: " + e.getMessage(), e);
        }
    }

    public void removeSemesterFromCourses(String payload){
        ObjectMapper mapper = new ObjectMapper();
        Long id;
        try {
            id = mapper.readValue(payload, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        getCoursesBySemesterId(id).forEach(course -> {
            course.setSemesterId(null);
            updateCourse(course.getCourseId(), course);
        });
    }


}
