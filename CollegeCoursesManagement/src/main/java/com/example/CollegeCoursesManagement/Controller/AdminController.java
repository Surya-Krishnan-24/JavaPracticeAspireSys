package com.example.CollegeCoursesManagement.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.CollegeCoursesManagement.DTO.CreateDepartmentRequest;
import com.example.CollegeCoursesManagement.DTO.CreateInstructorRequest;
import com.example.CollegeCoursesManagement.DTO.CreateInstructorRes;
import com.example.CollegeCoursesManagement.DTO.CreateStudentRequest;
import com.example.CollegeCoursesManagement.DTO.CreateStudentRes;
import com.example.CollegeCoursesManagement.DTO.DashboardResponse;
import com.example.CollegeCoursesManagement.DTO.UpdateInstructorRequest;
import com.example.CollegeCoursesManagement.DTO.UpdateStudentRequest;
import com.example.CollegeCoursesManagement.Model.Department;
import com.example.CollegeCoursesManagement.Service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

	
	private final AdminService adminService;
	
	@GetMapping("/dashboard")
	public DashboardResponse getDashboard() {
		return adminService.getDashboardData();
	}

	@GetMapping("/students")
	public ResponseEntity<List<CreateStudentRes>> getAllStudents() {
		List<CreateStudentRes> students = adminService.getAllStudents();
		return ResponseEntity.ok(students);
	}

	@PostMapping("/students")
	public ResponseEntity<?> createStudent(@Validated @RequestBody CreateStudentRequest request) {
		try {
			CreateStudentRes student = adminService.createStudent(request);
			return ResponseEntity.ok(student);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody UpdateStudentRequest request) {
		try {
			CreateStudentRes updated = adminService.updateStudent(id, request);
			return ResponseEntity.ok(updated);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
		try {
			adminService.deleteStudent(id);
			return ResponseEntity.ok("Student deleted successfully.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/instructors")
	public ResponseEntity<List<CreateInstructorRes>> getAllInstructors() {
		List<CreateInstructorRes> instructors = adminService.getAllInstructors();
		return ResponseEntity.ok(instructors);
	}

	@PostMapping("/instructors")
	public ResponseEntity<?> createInstructor(@Validated @RequestBody CreateInstructorRequest request) {
		try {
			CreateInstructorRes instructor = adminService.createInstructor(request);
			return ResponseEntity.ok(instructor);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/instructors/{id}")
	public ResponseEntity<?> updateInstructor(@PathVariable Long id, @RequestBody UpdateInstructorRequest request) {
		try {
			CreateInstructorRes updated = adminService.updateInstructor(id, request);
			return ResponseEntity.ok(updated);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/instructors/{id}")
	public ResponseEntity<?> deleteInstructor(@PathVariable Long id) {
		try {
			adminService.deleteInstructor(id);
			return ResponseEntity.ok("Instructor deleted successfully.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/departments")
	public ResponseEntity<List<Department>> getAllDepartments() {
		return ResponseEntity.ok(adminService.getAllDepartments());
	}

	@PostMapping("/departments")
	public ResponseEntity<?> createDepartment(@Validated @RequestBody CreateDepartmentRequest request) {
		try {
			Department created = adminService.createDepartment(request);
			return ResponseEntity.ok(created);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/departments/{id}")
	public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody CreateDepartmentRequest request) {
		try {
			Department updated = adminService.updateDepartment(id, request);
			return ResponseEntity.ok(updated);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/departments/{id}")
	public ResponseEntity<?> deleteDepartments(@PathVariable Long id) {
		try {
			adminService.deleteDepartment(id);
			return ResponseEntity.ok("Department deleted successfully.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
