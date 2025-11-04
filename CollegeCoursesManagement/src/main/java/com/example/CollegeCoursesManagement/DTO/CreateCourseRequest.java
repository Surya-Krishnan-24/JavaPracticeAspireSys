package com.example.CollegeCoursesManagement.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCourseRequest {
	
	@NotBlank
    private String title;
    
    private String thumbnailUrl;
}