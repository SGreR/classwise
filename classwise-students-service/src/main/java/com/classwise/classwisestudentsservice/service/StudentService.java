package com.classwise.classwisestudentsservice.service;

import com.classwise.classwisestudentsservice.model.Student;
import com.classwise.classwisestudentsservice.repository.StudentRepository;
import com.classwise.classwisestudentsservice.util.MessageBuilderUtil;
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
public class StudentService {

    private final StudentRepository studentRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public StudentService(StudentRepository studentRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.studentRepository = studentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsWithFilters(String byName){
        return studentRepository.findStudentsWithFilters(byName);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public List<Student> getStudentsByIds(List<Long> ids) {
        return studentRepository.findAllById(ids);
    }

    public List<Student> getStudentsByCourseId(Long id) {
        return studentRepository.findAll().stream().filter(student -> student.getCourseIds().contains(id)).toList();
    }

    public void addStudent(Student student) {
        try{
            Student newStudent = studentRepository.save(student);
            Message message = MessageBuilderUtil.buildMessage("student-events", "student-created", newStudent);
            kafkaTemplate.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStudent(Long id, Student newStudent) {
        try {
            Student oldStudent = studentRepository.findById(id).orElseThrow();
            newStudent.setStudentId(oldStudent.getStudentId());
            studentRepository.save(newStudent);
            Message message = MessageBuilderUtil.buildMessage("student-events", "student-updated", newStudent);
            kafkaTemplate.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(Long id) {
        try{
            studentRepository.findById(id).orElseThrow();
            studentRepository.deleteById(id);
            Message message = MessageBuilderUtil.buildMessage("student-events", "student-deleted", id);
            kafkaTemplate.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeCourseFromStudents(String payload){
        ObjectMapper mapper = new ObjectMapper();
        Long id;
        try {
            id = mapper.readValue(payload, Long.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        getStudentsByCourseId(id).forEach(student -> {
            Set<Long> courseIds = student.getCourseIds();
            courseIds.remove(id);
            student.setCourseIds(courseIds);
            updateStudent(student.getStudentId(), student);
        });
    }

    public void removeCourseFromStudents(Long courseId, Set<Long> studentIds) {
        studentIds.forEach(studentId -> {
            Student student = getStudentById(studentId);
            Set<Long> courseIds = student.getCourseIds();
            if (courseIds.contains(courseId)) {
                courseIds.remove(courseId);
                student.setCourseIds(courseIds);
                updateStudent(studentId, student);
            }
        });
    }

    public void addCoursesToStudents(Long courseId, Set<Long> studentIds) {
        studentIds.forEach(studentId -> {
            Student student = getStudentById(studentId);
            Set<Long> courseIds = student.getCourseIds();
            if (!courseIds.contains(courseId)) {
                courseIds.add(courseId);
                student.setCourseIds(courseIds);
                updateStudent(studentId, student);
            }
        });
    }

    public void matchCoursesAndStudents(String payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(payload);

            if (jsonNode.isObject()) {
                Long courseId = jsonNode.get("courseId").asLong();
                Set<Long> newStudentIds = mapper.convertValue(jsonNode.get("studentIds"), new TypeReference<Set<Long>>() {});
                Set<Long> newStudentSet = new HashSet<>(newStudentIds);

                Set<Long> currentStudentIds = getStudentsByCourseId(courseId).stream()
                        .map(Student::getStudentId)
                        .collect(Collectors.toSet());

                Set<Long> studentsToAdd = new HashSet<>(newStudentSet);
                studentsToAdd.removeAll(currentStudentIds);

                Set<Long> studentsToRemove = new HashSet<>(currentStudentIds);
                studentsToRemove.removeAll(newStudentSet);

                if (!studentsToAdd.isEmpty()) {
                    addCoursesToStudents(courseId, studentsToAdd);
                }

                if (!studentsToRemove.isEmpty()) {
                    removeCourseFromStudents(courseId, studentsToRemove);
                }
            } else {
                throw new IllegalArgumentException("Invalid JSON format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to match courses and students: " + e.getMessage(), e);
        }
    }
}

