package com.classwise.classwisegatewayservice.model;

import lombok.Data;
import java.util.Set;

@Data
public class AbilitiesDTO {
    private Long abilitiesId;
    private Set<SkillDTO> skills;
    private SpeakingDTO speaking;
    private ClassPerformanceDTO classPerformance;
}
