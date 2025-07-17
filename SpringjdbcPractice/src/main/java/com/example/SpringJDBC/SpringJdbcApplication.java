package com.example.SpringJDBC;

import com.example.SpringJDBC.model.Student;
import com.example.SpringJDBC.service.StudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class SpringJdbcApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringJdbcApplication.class, args);
		Student student = context.getBean(Student.class);
		student.setRollNo(105);
		student.setName("Aarya");
		student.setMarks(65);

		StudentService studentService = context.getBean(StudentService.class);
		studentService.addStudent(student);

		List<Student> students = studentService.getStudents();
		System.out.println(students);
	}

}
