package com.example.CollegeCoursesManagement.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.CollegeCoursesManagement.Model.ContentFile;
import com.example.CollegeCoursesManagement.Model.Course;

public interface InstructorService {
	
	public Course createCourseWithFile(String title, MultipartFile file, String username);
	
	public List<Course> getCoursesByInstructorUsername(String username);
	
	public Course getCourseDetails(Long courseId);
	
	public void addSubtopic(Long courseId, String title);
	
	public ContentFile uploadContentToSubtopic(Long subtopicId, MultipartFile file, String description);

}
