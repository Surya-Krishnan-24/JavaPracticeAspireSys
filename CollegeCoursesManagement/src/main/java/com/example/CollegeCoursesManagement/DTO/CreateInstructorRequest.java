package com.example.CollegeCoursesManagement.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateInstructorRequest {

	@NotBlank
	private String username;

	@Email
	@NotBlank
	private String email;

	@Size(min = 4)
	@NotBlank
	private String password;

	private String fullName;

	private Long departmentId;
}
