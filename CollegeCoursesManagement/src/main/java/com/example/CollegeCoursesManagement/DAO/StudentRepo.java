package com.example.CollegeCoursesManagement.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CollegeCoursesManagement.Model.Student;
import com.example.CollegeCoursesManagement.Model.User;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

	Optional<Student> findByUser(User user);

	long countByDepartment_DepartmentId(Long departmentId);

}
