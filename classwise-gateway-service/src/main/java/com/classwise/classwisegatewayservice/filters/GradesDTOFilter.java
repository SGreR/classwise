package com.classwise.classwisegatewayservice.filters;

import lombok.Data;

@Data
public class GradesDTOFilter {
    private boolean includeStudent;
    private boolean includeCourse;
}
