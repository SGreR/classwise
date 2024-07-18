package com.classwise.classwisegradesservice.model;

import com.classwise.classwisegradesservice.enums.SkillName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.DoubleStream;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Abilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long abilitiesId = null;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Skill> skills = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="speaking_id")
    private Speaking speaking = new Speaking();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="classperformance_id")
    private ClassPerformance classPerformance = new ClassPerformance();
    private double finalGrade;

    @PrePersist
    @PreUpdate
    private void calculateFinalGrade(){
        if (skills == null || skills.isEmpty()) {
            initializeDefaultSkills();
        }

        DoubleStream skills = this.skills.stream().mapToDouble(Skill::getAverageGrade);
        double speakingGrade = this.speaking.getAverageGrade();
        double classPerformanceGrade = this.classPerformance.getAverageGrade();
        double averageGrade = DoubleStream.concat(
                skills,
                DoubleStream.of(speakingGrade, classPerformanceGrade))
                .average()
                .orElse(0);
        this.finalGrade = Math.round(averageGrade * 100.0) / 100.0;
    }

    private void initializeDefaultSkills() {
        skills = new HashSet<>(Arrays.asList(
                new Skill(SkillName.READING, 0.0, 0.0),
                new Skill(SkillName.LISTENING, 0.0, 0.0),
                new Skill(SkillName.USEOFENGLISH, 0.0, 0.0),
                new Skill(SkillName.WRITING, 0.0, 0.0)
        ));
    }
}

