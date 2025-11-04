package com.example.CollegeCoursesManagement.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CollegeCoursesManagement.Model.Course;
import com.example.CollegeCoursesManagement.Model.Instructor;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {

	List<Course> findByInstructor(Instructor instructor);

}
