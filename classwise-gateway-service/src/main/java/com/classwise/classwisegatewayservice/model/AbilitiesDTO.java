package com.classwise.classwisegatewayservice.model;

import lombok.Data;

import java.util.List;

@Data
public class AbilitiesDTO {
    private Long abilitiesId;
    private List<SkillDTO> skills;
    private SpeakingDTO speaking;
    private ClassPerformanceDTO classPerformance;
}
