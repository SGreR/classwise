package com.classwise.classwisegatewayservice.filters;

import lombok.Data;

@Data
public class CourseDTOFilter {
    private boolean includeStudents;
    private boolean includeTeacher;
    private boolean includeSemester;
    private boolean includeGrades;
}
