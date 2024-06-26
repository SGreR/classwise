package com.classwise.classwisegradesservice.model;

import com.classwise.classwisegradesservice.enums.SkillName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;
    @Enumerated(EnumType.STRING)
    private SkillName skillName;
    @OneToOne
    @JoinColumn(name = "grades_id", referencedColumnName = "gradesId")
    private Grades grades;
    private double teacherGrade = 0.0;
    private double testGrade = 0.0;
    private double averageGrade;

    @PrePersist
    @PreUpdate
    private void calculateAverageGrade(){
        this.averageGrade = Arrays.stream(new double[] {teacherGrade, testGrade}).average().orElse(0);
    }
}
