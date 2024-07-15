package com.classwise.classwisegatewayservice.filters;

import lombok.Data;

import java.util.Optional;

@Data
public class SemesterDTOFilter {
    Optional<Integer> bySchoolYear = Optional.empty();
    Optional<Integer> bySemesterNumber = Optional.empty();
    private boolean includeCourses = false;
}
