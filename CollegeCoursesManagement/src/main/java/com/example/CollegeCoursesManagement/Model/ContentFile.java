package com.example.CollegeCoursesManagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName; 
    
    private String fileUrl;   
    
    private String fileType;    
    
    private String description;  

    @ManyToOne
    @JoinColumn(name = "subtopic_id")
    @JsonBackReference 
    private Subtopic subtopic;
}
