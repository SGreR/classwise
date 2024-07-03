package com.classwise.classwisecourseservices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

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
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> studentIds;
}
