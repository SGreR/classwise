package com.classwise.classwisegatewayservice.filters;

import lombok.Data;

@Data
public class StudentDTOFilter {
    private boolean includeCourses;
    private boolean includeGrades;
}
