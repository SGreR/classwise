package com.classwise.classwisegatewayservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentDTO {
    public Long studentId;
    private String studentName;
    private List<Long> courseIds = new ArrayList<>();
    private List<CourseDTO> courses = new ArrayList<>();
}
