package com.classwise.classwisecourseservices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long courseId;
    private String courseName;
    private Long semesterId;
    private Long teacherId;
    private boolean active;
    @ElementCollection
    private List<Long> studentIds;
}
