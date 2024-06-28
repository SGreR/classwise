package com.classwise.classwisegatewayservice.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServiceURLs {

    @Value("${service.course.url}")
    private String courseUrl;

    @Value("${service.grades.url}")
    private String gradesUrl;

    @Value("${service.semester.url}")
    private String semesterUrl;

    @Value("${service.students.url}")
    private String studentsUrl;

    @Value("${service.teachers.url}")
    private String teachersUrl;

}

