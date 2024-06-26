package com.classwise.classwisegradesservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ClassPerformance {
    public Long classPerformanceId;
    private Long gradesId;
    private int presenceGrade;
    private int homeworkGrade;
    private int participationGrade;
    private int behaviorGrade;
    public double averageGrade = calculateAverage();

    private double calculateAverage(){
        int[] grades = {presenceGrade, homeworkGrade, participationGrade, behaviorGrade};
        return (double) Arrays.stream(grades).sum() / 2;
    }

}
