package com.classwise.classwisegatewayservice.filters;

import lombok.Data;

import java.util.Optional;

@Data
public class StudentDTOFilter {
    private Optional<String> byName = Optional.empty();
    private boolean includeCourses = false;
    private boolean includeGrades = false;
}
