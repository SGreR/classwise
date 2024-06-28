package com.classwise.classwisegradesservice.model;

import com.classwise.classwisegradesservice.enums.TestNumber;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long gradesId;
    private Long studentId;
    private Long courseId;
    @Enumerated(EnumType.STRING)
    private TestNumber testNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="abilities_id")
    private Abilities abilities;
}
