package com.classwise.classwisegatewayservice.model;

import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class StudentDTO {
    public Long studentId;
    private String studentName;
    private Set<Long> courseIds = new HashSet<>();
    private Set<CourseDTO> courses = new HashSet<>();
}
