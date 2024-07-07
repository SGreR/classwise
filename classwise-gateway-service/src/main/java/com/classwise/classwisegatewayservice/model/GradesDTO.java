package com.classwise.classwisegatewayservice.model;

import lombok.Data;

@Data
public class GradesDTO {
    public Long gradesId;
    private Long studentId;
    private StudentDTO student;
    private Long courseId;
    private CourseDTO course;
    private String testNumber;
    private AbilitiesDTO abilities;
}
