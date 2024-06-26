package com.classwise.classwisegatewayservice.model;

import lombok.Data;

import java.util.List;

@Data
public class CourseDTO {
    public Long courseId;
    private String courseName;
    private SemesterDTO semester;
    private TeacherDTO teacher;
    private List<Long> studentIds;
    private List<StudentDTO> students;

}
