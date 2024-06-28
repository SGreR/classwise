package com.classwise.classwisegatewayservice.model;

import lombok.Data;

@Data
public class SemesterDTO {
    public Long semesterId;
    private int schoolYear;
    private int semesterNumber;

}
