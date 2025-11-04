package com.example.CollegeCoursesManagement.Service;

import com.example.CollegeCoursesManagement.DAO.CourseRepo;
import com.example.CollegeCoursesManagement.DAO.StudentRepo;
import com.example.CollegeCoursesManagement.DAO.UserRepo;
import com.example.CollegeCoursesManagement.Model.Course;
import com.example.CollegeCoursesManagement.Model.Student;
import com.example.CollegeCoursesManagement.Model.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

   
    private final UserRepo userRepo;

   
    private final StudentRepo studentRepo;


    private final CourseRepo courseRepo;

  
    @Override
    public List<Course> getAllCourses(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Student student = studentRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        
        List<Course> courses = courseRepo.findAll();

        
        for (Course course : courses) {
            boolean isEnrolled = student.getCourses().contains(course);
            
            course.setEnrolled(isEnrolled);  
        }

        return courses;
    }


    @Override
    public Course getCourseDetails(Long courseId) {
        return courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

   
    @Override
    public void enrollStudentInCourse(String username, Long courseId) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Student student = studentRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        
        if (student.getCourses().contains(course)) {
            throw new RuntimeException("Student is already enrolled in this course");
        }

       
        student.getCourses().add(course);
        studentRepo.save(student);
    }

    @Override
    public List<Course> getEnrolledCourses(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Student student = studentRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return student.getCourses();
    }
}
