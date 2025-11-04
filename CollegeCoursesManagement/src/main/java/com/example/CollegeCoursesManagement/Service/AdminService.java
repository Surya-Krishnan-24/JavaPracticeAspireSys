package com.example.CollegeCoursesManagement.Service;

import java.util.List;

import com.example.CollegeCoursesManagement.DTO.CreateDepartmentRequest;
import com.example.CollegeCoursesManagement.DTO.CreateInstructorRequest;
import com.example.CollegeCoursesManagement.DTO.CreateInstructorRes;
import com.example.CollegeCoursesManagement.DTO.CreateStudentRequest;
import com.example.CollegeCoursesManagement.DTO.CreateStudentRes;
import com.example.CollegeCoursesManagement.DTO.DashboardResponse;
import com.example.CollegeCoursesManagement.DTO.UpdateInstructorRequest;
import com.example.CollegeCoursesManagement.DTO.UpdateStudentRequest;
import com.example.CollegeCoursesManagement.Model.Department;

public interface AdminService {

	DashboardResponse getDashboardData();

	List<CreateStudentRes> getAllStudents();

	CreateStudentRes createStudent(CreateStudentRequest request);

	CreateStudentRes updateStudent(Long id, UpdateStudentRequest request);

	void deleteStudent(Long id);

	List<CreateInstructorRes> getAllInstructors();

	CreateInstructorRes createInstructor(CreateInstructorRequest request);

	CreateInstructorRes updateInstructor(Long id, UpdateInstructorRequest request);

	void deleteInstructor(Long id);

	List<Department> getAllDepartments();

	Department createDepartment(CreateDepartmentRequest request);

	Department updateDepartment(Long id, CreateDepartmentRequest request);

	void deleteDepartment(Long id);

}
