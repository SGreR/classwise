package com.classwise.classwisegatewayservice.model;

import lombok.Data;

import java.util.Arrays;

@Data
public class ClassPerformanceDTO {

    private Long classPerformanceId;
    private int presenceGrade;
    private int homeworkGrade;
    private int participationGrade;
    private int behaviorGrade;
    public double averageGrade = calculateAverageGrade();

    private double calculateAverageGrade(){
        int[] grades = {presenceGrade, homeworkGrade, participationGrade, behaviorGrade};
        return (double) Arrays.stream(grades).sum() / 2;
    }
}
