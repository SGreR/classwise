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
public class Speaking {
    public Long speakingId;
    private Long gradesId;
    private int productionAndFluencyGrade;
    private int spokenInteractionGrade;
    private int languageRangeGrade;
    private int accuracyGrade;
    private int languageUse;
    private double averageGrade = calculateAverage();

    public double calculateAverage(){
        int[] grades = {productionAndFluencyGrade, spokenInteractionGrade, languageRangeGrade, accuracyGrade};
        double sumGrades = Arrays.stream(grades).sum();
        double weightedSum = sumGrades * 5;

        double multiplier = (5 - languageUse) * 0.1;
        double finalScore = weightedSum - (weightedSum * multiplier);

        return finalScore / 10;
    }

}
