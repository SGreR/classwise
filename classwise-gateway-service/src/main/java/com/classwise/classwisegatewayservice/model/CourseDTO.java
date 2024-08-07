package com.classwise.classwisegatewayservice.model;

import lombok.Data;
import java.util.Set;

@Data
public class CourseDTO {
    public Long courseId;
    private String courseName;
    private boolean active;
    private Long semesterId;
    private SemesterDTO semester;
    private Long teacherId;
    private TeacherDTO teacher;
    private Set<Long> studentIds;
    private Set<StudentDTO> students;
    private Set<GradesDTO> grades;

}
