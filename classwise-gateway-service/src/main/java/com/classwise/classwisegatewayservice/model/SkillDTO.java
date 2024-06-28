package com.classwise.classwisegatewayservice.model;

import lombok.Data;

import java.util.Arrays;

@Data
public class SkillDTO {
    public Long skillId;
    private String skillName;
    private double teacherGrade;
    private double testGrade;
    private double averageGrade = calculateAverageGrade();

    private double calculateAverageGrade(){
        return Arrays.stream(new double[] {teacherGrade, testGrade}).average().orElse(0);
    }
}
