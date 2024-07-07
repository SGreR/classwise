package com.classwise.classwisegatewayservice.model;

import lombok.Data;

import java.util.Set;

@Data
public class SemesterDTO {
    public Long semesterId;
    private int schoolYear;
    private int semesterNumber;
    private Set<CourseDTO> courses;

}
