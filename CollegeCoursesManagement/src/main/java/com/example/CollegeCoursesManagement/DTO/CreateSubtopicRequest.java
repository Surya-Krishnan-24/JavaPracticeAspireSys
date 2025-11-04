package com.example.CollegeCoursesManagement.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSubtopicRequest {
	
	@NotBlank
    private String title;
}