package com.example.CollegeCoursesManagement.DTO;

import lombok.Data;

@Data
public class CreateInstructorRes {

	private String instructorId;

	private String username;

	private String email;

	private String fullName;

	private String departmentName;

	private long DepartmentId;
}
