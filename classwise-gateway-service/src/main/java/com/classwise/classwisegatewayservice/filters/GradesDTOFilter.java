package com.classwise.classwisegatewayservice.filters;

import lombok.Data;

import java.util.Optional;

@Data
public class GradesDTOFilter {
    private Optional<String> byStudent = Optional.empty();
    private Optional<String> byCourse = Optional.empty();
    private Optional<Integer> bySemester = Optional.empty();
    private Optional<Integer> byYear = Optional.empty();
    private Optional<String> byTeacher = Optional.empty();
    private boolean includeStudent = false;
    private boolean includeCourse = false;
}
