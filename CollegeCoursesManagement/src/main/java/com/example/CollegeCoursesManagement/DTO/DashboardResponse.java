package com.example.CollegeCoursesManagement.DTO;

import java.util.List;

import lombok.Data;

@Data
public class DashboardResponse {

	private long departmentCount;

	private long instructorCount;

	private long studentCount;

	private long courseCount;

	private List<DepartmentStats> studentsPerDepartment;

	private List<YearStats> studentsPerYear;

	private List<InstructorStats> instructorsPerDepartment;

	@Data
	public static class DepartmentStats {

		private String department;

		private long count;
	}

	@Data
	public static class YearStats {

		private String year;

		private long count;
	}

	@Data
	public static class InstructorStats {
		private String department;

		private long count;
	}
}
