package com.classwise.classwisegatewayservice.model;

import lombok.Data;

import java.util.Arrays;

@Data
public class SpeakingDTO {

    private Long speakingId;
    private int productionAndFluencyGrade;
    private int spokenInteractionGrade;
    private int languageRangeGrade;
    private int accuracyGrade;
    private int languageUse;
    private double averageGrade = calculateAverageGrade();

    private double calculateAverageGrade(){
        int[] grades = {productionAndFluencyGrade, spokenInteractionGrade, languageRangeGrade, accuracyGrade};
        double sumGrades = Arrays.stream(grades).sum();
        double weightedSum = sumGrades * 5;

        double multiplier = (5 - languageUse) * 0.1;
        double finalScore = weightedSum - (weightedSum * multiplier);

        return finalScore / 10;
    }
}
