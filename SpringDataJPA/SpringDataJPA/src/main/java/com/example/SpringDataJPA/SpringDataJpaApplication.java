package com.example.SpringDataJPA;

import com.example.SpringDataJPA.model.Student;
import com.example.SpringDataJPA.repo.StudentRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringDataJpaApplication.class, args);
		Student s1 = context.getBean(Student.class);
		Student s2 = context.getBean(Student.class);
		Student s3 = context.getBean(Student.class);

		StudentRepo repo = context.getBean(StudentRepo.class);

		s1.setRollNo(101);
		s1.setName("Surya");
		s1.setMarks(90);

		s2.setRollNo(102);
		s2.setName("Senthil");
		s2.setMarks(78);

		s3.setRollNo(103);
		s3.setName("Suchind");
		s3.setMarks(80);


//		repo.save(s1);
//		repo.save(s2);
//		repo.save(s3);

//		System.out.println(repo.findById(103));

//		System.out.println(repo.findByName("Surya"));

//		repo.save(s1);

		repo.delete(s3);

	}

}
