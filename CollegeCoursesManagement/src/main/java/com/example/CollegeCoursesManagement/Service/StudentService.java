package com.example.CollegeCoursesManagement.Service;

import java.util.List;

import com.example.CollegeCoursesManagement.Model.Course;

public interface StudentService {
	
	 public List<Course> getAllCourses(String username);
	 
	 public Course getCourseDetails(Long courseId);
	 
	 public void enrollStudentInCourse(String username, Long courseId);
	 
	 public List<Course> getEnrolledCourses(String username);

}
