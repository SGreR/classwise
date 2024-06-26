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
public class Speaking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long speakingId;

    @OneToOne
    @JoinColumn(name = "grades_id", referencedColumnName = "gradesId")
    private Grades grades;
    private int productionAndFluencyGrade;
    private int spokenInteractionGrade;
    private int languageRangeGrade;
    private int accuracyGrade;
    private int languageUse;
    private double averageGrade;

    @PrePersist
    @PreUpdate
    private void calculateAverageGrade(){
        int[] grades = {productionAndFluencyGrade, spokenInteractionGrade, languageRangeGrade, accuracyGrade};
        double sumGrades = Arrays.stream(grades).sum();
        double weightedSum = sumGrades * 5;

        double multiplier = (5 - languageUse) * 0.1;
        double finalScore = weightedSum - (weightedSum * multiplier);

        this.averageGrade = finalScore / 10;
    }

}
