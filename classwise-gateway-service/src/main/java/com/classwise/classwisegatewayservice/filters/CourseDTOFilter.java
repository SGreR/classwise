package com.classwise.classwisegatewayservice.filters;

import lombok.Data;

@Data
public class CourseDTOFilter {
    private boolean includeStudents = false;
    private boolean includeTeacher = false;
    private boolean includeSemester = false;
}
