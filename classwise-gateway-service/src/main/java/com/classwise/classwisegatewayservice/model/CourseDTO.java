package com.classwise.classwisegatewayservice.model;

import lombok.Data;

import java.util.List;

@Data
public class CourseDTO {
    public Long courseId;
    private String courseName;
    private Long semesterId;
    private SemesterDTO semester;
    private Long teacherId;
    private TeacherDTO teacher;
    private List<Long> studentIds;
    private List<StudentDTO> students;

}
