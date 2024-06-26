package com.classwise.classwisegradesservice.model;

import com.classwise.classwisegradesservice.enums.TestNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Grades {
    public Long gradesId;
    private Long studentId;
    private Long courseId;
    private TestNumber testNumber;
    private Skills skills;
    private Speaking speaking;
    private ClassPerformance classPerformance;
}
