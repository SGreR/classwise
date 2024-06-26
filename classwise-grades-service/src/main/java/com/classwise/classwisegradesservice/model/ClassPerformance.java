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
    private Long classPerformanceId;

    @OneToOne
    @JoinColumn(name = "grades_id", referencedColumnName = "gradesId")
    private Grades grades;
    private int presenceGrade;
    private int homeworkGrade;
    private int participationGrade;
    private int behaviorGrade;
    public double averageGrade;

    @PrePersist
    @PreUpdate
    private void calculateAverageGrade(){
        int[] grades = {presenceGrade, homeworkGrade, participationGrade, behaviorGrade};
        this.averageGrade = (double) Arrays.stream(grades).sum() / 2;
    }

}
