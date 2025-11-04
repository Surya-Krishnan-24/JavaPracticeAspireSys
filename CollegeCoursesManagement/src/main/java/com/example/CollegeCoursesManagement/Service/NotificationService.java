package com.example.CollegeCoursesManagement.Service;

import com.example.CollegeCoursesManagement.Model.Instructor;
import com.example.CollegeCoursesManagement.Model.Student;
import com.example.CollegeCoursesManagement.Model.User;

public interface NotificationService {

	void createAccount(User user, Student student, Instructor instructor);

}
