package com.classwise.classwisegatewayservice.model;

import lombok.Data;

@Data
public class GradesDTO {
    public Long gradesId;
    private Long studentId;
    private Long courseId;
    private String testNumber;
    private AbilitiesDTO abilities;
}
