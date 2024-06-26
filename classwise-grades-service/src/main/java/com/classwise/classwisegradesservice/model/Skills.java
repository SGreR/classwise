package com.classwise.classwisegradesservice.model;

import com.classwise.classwisegradesservice.enums.SkillName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Skills {
    private Long skillId;
    private SkillName skillName;
    private double teacherGrade = 0.0;
    private double testGrade = 0.0;
    private double averageGrade = calculateAverage();

    private double calculateAverage(){
        return Arrays.stream(new double[] {teacherGrade, testGrade}).average().orElse(0);
    }
}
