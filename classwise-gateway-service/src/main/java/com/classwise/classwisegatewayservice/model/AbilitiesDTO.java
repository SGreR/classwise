package com.classwise.classwisegatewayservice.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.DoubleStream;

@Data
public class AbilitiesDTO {
    private Long abilitiesId;
    private Set<SkillDTO> skills = new HashSet<>();
    private SpeakingDTO speaking = new SpeakingDTO();
    private ClassPerformanceDTO classPerformance = new ClassPerformanceDTO();
    private double finalGrade = 0;

    private double calculateFinalGrade() {
        DoubleStream skillGrades = this.skills.stream().mapToDouble(SkillDTO::getAverageGrade);
        double speakingGrade = this.speaking.getAverageGrade();
        double classPerformanceGrade = this.classPerformance.getAverageGrade();

        return DoubleStream.concat(
                        skillGrades,
                        DoubleStream.of(speakingGrade, classPerformanceGrade))
                .average()
                .orElse(0);
    }
}
