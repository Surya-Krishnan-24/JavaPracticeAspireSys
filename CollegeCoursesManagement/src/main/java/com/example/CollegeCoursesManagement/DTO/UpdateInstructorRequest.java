package com.example.CollegeCoursesManagement.DTO;

import lombok.Data;

@Data
public class UpdateInstructorRequest {

	private String username;
	
	private String fullName;
	
	private String email;
	
	private String password;
	
	private Long departmentId;
}
