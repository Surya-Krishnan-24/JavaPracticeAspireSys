package com.example.CollegeCoursesManagement.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CollegeCoursesManagement.Model.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {

	boolean existsByDeptCode(String deptCode);

}
