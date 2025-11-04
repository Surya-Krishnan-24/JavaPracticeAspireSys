//package com.example.CollegeCoursesManagement.Config;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import com.example.CollegeCoursesManagement.DAO.UserRepo;
//import com.example.CollegeCoursesManagement.Model.User;
//
//@Configuration
//public class AdminInitializer {
//
//	@Bean
//	CommandLineRunner initAdmin(UserRepo userRepo) {
//		return args -> {
//			if (userRepo.findByUsername("admin").isEmpty()) {
//				User admin = new User();
//				admin.setUsername("admin");
//				admin.setEmail("admin@college.com");
//				admin.setPassword(new BCryptPasswordEncoder(12).encode("admin123"));
//				admin.setRole("ROLE_ADMIN");
//				userRepo.save(admin);
//				System.out.println("Default admin account created: username=admin, password=admin123");
//			} else {
//				System.out.println("Admin account already exists. Skipping creation.");
//			}
//		};
//	}
//}
