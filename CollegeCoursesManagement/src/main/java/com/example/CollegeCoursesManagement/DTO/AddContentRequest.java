package com.example.CollegeCoursesManagement.DTO;

import lombok.Data;

@Data
public class AddContentRequest {
	
    private String fileUrl;
    
    private String fileType;
}