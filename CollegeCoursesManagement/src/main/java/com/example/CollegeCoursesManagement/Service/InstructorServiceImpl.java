package com.example.CollegeCoursesManagement.Service;

import com.example.CollegeCoursesManagement.DAO.*;

import com.example.CollegeCoursesManagement.Model.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService  {

    private final UserRepo userRepo;
    
    private final InstructorRepo instructorRepo;
    
    private final CourseRepo courseRepo;
    
    private final SubtopicRepo subtopicRepo;
    
    private final ContentFileRepo contentFileRepo;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";


    @Override
    public Course createCourseWithFile(String title, MultipartFile file, String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Instructor instructor = instructorRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        ensureUploadDirExists();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File destination = new File(UPLOAD_DIR, fileName);
        try {
            file.transferTo(destination);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }

        Course course = new Course();
        course.setTitle(title);
        course.setInstructor(instructor);
        course.setThumbnailUrl("/files/" + fileName);

        return courseRepo.save(course);
    }

    @Override
    public List<Course> getCoursesByInstructorUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Instructor instructor = instructorRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        return courseRepo.findByInstructor(instructor);
    }


    @Override
    public Course getCourseDetails(Long courseId) {
        return courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }


    @Override
    public void addSubtopic(Long courseId, String title) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Subtopic subtopic = new Subtopic();
        subtopic.setTitle(title);
        subtopic.setCourse(course);
        subtopicRepo.save(subtopic);
    }


    @Override
    public ContentFile uploadContentToSubtopic(Long subtopicId, MultipartFile file, String description) {
        Subtopic subtopic = subtopicRepo.findById(subtopicId)
                .orElseThrow(() -> new RuntimeException("Subtopic not found"));

        ensureUploadDirExists();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String fileType = getFileExtension(file.getOriginalFilename());
        File destination = new File(UPLOAD_DIR, fileName);

        try {
            file.transferTo(destination);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }

        ContentFile content = new ContentFile();
        content.setSubtopic(subtopic);
        content.setFileName(file.getOriginalFilename());
        content.setFileUrl("/files/" + fileName);
        content.setFileType(fileType);
        content.setDescription(description);

        return contentFileRepo.save(content);
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex != -1) ? fileName.substring(dotIndex + 1) : "unknown";
    }

    private void ensureUploadDirExists() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new RuntimeException("Failed to create upload directory: " + UPLOAD_DIR);
        }
    }
}
