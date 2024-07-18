package com.classwise.classwisegradesservice.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="classperformance_id")
    private Long classPerformanceId = null;
    private int presenceGrade = 0;
    private int homeworkGrade = 0;
    private int participationGrade = 0;
    private int behaviorGrade = 0;
    public double averageGrade;

    public double getAverageGrade() {
        return calculateAverageGrade();
    }

    private double calculateAverageGrade(){
        int[] grades = {presenceGrade, homeworkGrade, participationGrade, behaviorGrade};
        return (double) Arrays.stream(grades).sum() / 2 * 10;
    }

}
