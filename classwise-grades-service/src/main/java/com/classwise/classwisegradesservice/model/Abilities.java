package com.classwise.classwisegradesservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Abilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long abilitiesId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Skill> skills;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="speaking_id")
    private Speaking speaking;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="classperformance_id")
    private ClassPerformance classPerformance;
}

