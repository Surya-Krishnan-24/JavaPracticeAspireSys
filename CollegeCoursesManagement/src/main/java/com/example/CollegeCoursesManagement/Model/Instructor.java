package com.example.CollegeCoursesManagement.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userId", nullable = false, unique = true)
    @JsonIgnoreProperties({"instructor", "student"}) 
    private User user;

    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;

    private String fullName;
}
