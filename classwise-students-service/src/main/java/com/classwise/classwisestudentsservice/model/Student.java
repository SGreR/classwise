package com.classwise.classwisestudentsservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long studentId;
    public String studentName;
    @ElementCollection(fetch = FetchType.EAGER)
    public Set<Long> courseIds;

}
