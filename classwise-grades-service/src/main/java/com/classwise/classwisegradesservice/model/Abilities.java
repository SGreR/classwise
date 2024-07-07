package com.classwise.classwisegradesservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
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
    private Long abilitiesId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Skill> skills;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="speaking_id")
    private Speaking speaking;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="classperformance_id")
    private ClassPerformance classPerformance;
    private double finalGrade;

    @PrePersist
    @PreUpdate
    private void calculateFinalGrade(){
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
}

