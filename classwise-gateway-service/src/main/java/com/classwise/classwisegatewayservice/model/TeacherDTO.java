package com.classwise.classwisegatewayservice.model;

import lombok.Data;

import java.util.Set;

@Data
public class TeacherDTO {
    public Long teacherId;
    public String teacherName;
    private Set<CourseDTO> courses;
}
