package com.classwise.classwiseclassroomservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Course {
    public Long courseId;
    private String courseName;
    private Long semesterId;
    private Long teacherId;
    private List<Long> studentIds;
}
