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
    public Long gradesId = null;
    private Long studentId = null;
    private Long courseId = null;
    @Enumerated(EnumType.STRING)
    private TestNumber testNumber = TestNumber.FIRST;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="abilities_id")
    private Abilities abilities = new Abilities();
}
