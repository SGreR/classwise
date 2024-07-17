package com.classwise.classwisegatewayservice.filters;

import lombok.Data;

import java.util.Optional;

@Data
public class CourseDTOFilter {
    private Optional<String> byName = Optional.empty();
    private Optional<Integer> byYear = Optional.empty();
    private Optional<Integer> bySemester = Optional.empty();
    private Optional<String> byTeacher = Optional.empty();
    private boolean includeStudents = false;
    private boolean includeTeacher = false;
    private boolean includeSemester = false;
    private boolean includeGrades = false;
}
