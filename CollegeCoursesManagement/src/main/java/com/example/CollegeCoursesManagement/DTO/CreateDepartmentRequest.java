package com.example.CollegeCoursesManagement.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDepartmentRequest {

	@NotBlank
	private String name;

	@NotBlank
	private String deptCode;

}
