package com.example.CollegeCoursesManagement.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CollegeCoursesManagement.Model.Instructor;
import com.example.CollegeCoursesManagement.Model.User;

@Repository
public interface InstructorRepo extends JpaRepository<Instructor, Long> {

	Optional<Instructor> findByUser(User user);

	long countByDepartment_DepartmentId(Long departmentId);
	
	Optional<Instructor> findByUser_Username(String username);

}
