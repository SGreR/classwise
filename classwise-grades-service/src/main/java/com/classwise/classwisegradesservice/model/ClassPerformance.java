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
    private Long classPerformanceId;
    private int presenceGrade;
    private int homeworkGrade;
    private int participationGrade;
    private int behaviorGrade;
    public double averageGrade;

    public double getAverageGrade() {
        return calculateAverageGrade();
    }

    private double calculateAverageGrade(){
        int[] grades = {presenceGrade, homeworkGrade, participationGrade, behaviorGrade};
        return (double) Arrays.stream(grades).sum() / 2 * 10;
    }

}
