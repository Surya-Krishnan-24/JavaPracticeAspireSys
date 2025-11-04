package com.example.CollegeCoursesManagement.Controller;

import com.example.CollegeCoursesManagement.Model.Course;
import com.example.CollegeCoursesManagement.Service.StudentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

   
    public final StudentService studentService;

    
    @GetMapping("/allcourses")
    public ResponseEntity<List<Course>> getAllCourses(Authentication authentication) {
        
        String username = authentication.getName();
       
        List<Course> courses = studentService.getAllCourses(username);
        return ResponseEntity.ok(courses);
    }

    
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Course> getCourseDetails(@PathVariable Long courseId) {
        return ResponseEntity.ok(studentService.getCourseDetails(courseId));
    }

    
    @PostMapping("/courses/{courseId}/enroll")
    public ResponseEntity<String> enrollCourse(
            @PathVariable Long courseId,
            Authentication authentication) {

        String username = authentication.getName();
        studentService.enrollStudentInCourse(username, courseId);
        return ResponseEntity.ok("Successfully enrolled in course!");
    }

    
    @GetMapping("/mycourses")
    public ResponseEntity<List<Course>> getMyCourses(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(studentService.getEnrolledCourses(username));
    }
}
