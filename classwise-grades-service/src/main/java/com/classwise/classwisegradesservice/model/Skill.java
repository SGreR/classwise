package com.classwise.classwisegradesservice.model;

import com.classwise.classwisegradesservice.enums.SkillName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long skillId;
    @Enumerated(EnumType.STRING)
    private SkillName skillName;
    private double teacherGrade = 0.0;
    private double testGrade = 0.0;
    private double averageGrade;

    public Skill(SkillName skillName, double teacherGrade, double testGrade) {
        this.skillName = skillName;
        this.teacherGrade = teacherGrade;
        this.testGrade = testGrade;
        this.averageGrade = calculateAverageGrade();
    }

    public double getAverageGrade() {
        return calculateAverageGrade();
    }

    private double calculateAverageGrade(){
        return Arrays.stream(new double[] {teacherGrade, testGrade}).average().orElse(0);
    }
}
