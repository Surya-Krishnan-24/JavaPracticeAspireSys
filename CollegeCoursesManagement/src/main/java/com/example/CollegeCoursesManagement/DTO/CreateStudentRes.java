package com.example.CollegeCoursesManagement.DTO;

import lombok.Data;

@Data
public class CreateStudentRes {

	private String studentId;

	private String username;

	private String email;

	private String fullName;

	private String departmentName;
}
