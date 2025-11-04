package com.example.CollegeCoursesManagement.Controller;

import com.example.CollegeCoursesManagement.Model.Course;
import com.example.CollegeCoursesManagement.Model.ContentFile;
import com.example.CollegeCoursesManagement.Service.InstructorService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class InstructorController {

	
    private final InstructorService instructorService;

    
    @PostMapping(value = "/courses", consumes = {"multipart/form-data"})
    public ResponseEntity<Course> createCourse(
            @RequestPart("title") String title,
            @RequestPart("file") MultipartFile file,
            Authentication authentication) {

        String username = authentication.getName();
        Course createdCourse = instructorService.createCourseWithFile(title, file, username);
        return ResponseEntity.ok(createdCourse);
    }

    
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getInstructorCourses(Authentication authentication) {
        String username = authentication.getName();
        List<Course> courses = instructorService.getCoursesByInstructorUsername(username);
        return ResponseEntity.ok(courses);
    }

    
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Course> getCourseDetails(@PathVariable Long courseId) {
        Course course = instructorService.getCourseDetails(courseId);
        return ResponseEntity.ok(course);
    }

    
    @PostMapping("/courses/{courseId}/subtopics")
    public ResponseEntity<String> addSubtopic(
            @PathVariable Long courseId,
            @RequestBody SubtopicRequest request) {

        instructorService.addSubtopic(courseId, request.getTitle());
        return ResponseEntity.ok("Subtopic added successfully!");
    }

    
    @PostMapping(value = "/subtopics/{subtopicId}/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<ContentFile> uploadContentToSubtopic(
            @PathVariable Long subtopicId,
            @RequestPart("file") MultipartFile file,
            @RequestPart(value = "description", required = false) String description) {

        ContentFile content = instructorService.uploadContentToSubtopic(subtopicId, file, description);
        return ResponseEntity.ok(content);
    }

   
    static class SubtopicRequest {
        private String title;
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
    }
}
