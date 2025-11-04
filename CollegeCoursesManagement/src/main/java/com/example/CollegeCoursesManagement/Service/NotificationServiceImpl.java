package com.example.CollegeCoursesManagement.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.CollegeCoursesManagement.Model.Instructor;
import com.example.CollegeCoursesManagement.Model.Student;
import com.example.CollegeCoursesManagement.Model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	
	private final JavaMailSender javaMailSender;

	@Override
	public void createAccount(User user, Student student, Instructor instructor) {
		if (user == null || user.getEmail() == null) {
			throw new IllegalArgumentException("User or user email cannot be null");
		}

		String fullName = "";
		System.out.println(student.getFullName());
		if (student.getFullName() != null) {
			fullName = student.getFullName();
		} else if (instructor.getFullName() != null) {
			fullName = instructor.getFullName();
		} else {
			fullName = capitalize(user.getUsername());
		}

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setSubject("Welcome to Aspire Course Management Portal");

		StringBuilder content = new StringBuilder();
		content.append("Hi ").append(capitalize(fullName)).append(",\n\n");

		content.append("🎉 Welcome to Aspire Course Management Portal!\n\n");
		String role = user.getRole().replace("ROLE_", "").toLowerCase();
		role = Character.toUpperCase(role.charAt(0)) + role.substring(1);
		content.append("Your account has been successfully created as a ").append(role).append(".\n\n");

		content.append("Here are your account details:\n");
		content.append("Username: ").append(user.getUsername()).append("\n");
		content.append("Email: ").append(user.getEmail()).append("\n");

		content.append("\nYou can now log in and explore your dashboard to view your courses.\n\n\n\n");

		content.append("Best regards,\n");
		content.append("Aspire Course Management Team");

		message.setText(content.toString());
		javaMailSender.send(message);
	}

	private String capitalize(String str) {
		if (str == null || str.isEmpty()) {
			return "";
		}
		str = str.trim();
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
}
