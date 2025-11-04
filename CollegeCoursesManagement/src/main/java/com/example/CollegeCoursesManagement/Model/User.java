package com.example.CollegeCoursesManagement.Model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;

	@Column(unique = true)
	@NotBlank
	private String username;

	@Column(unique = true)
	@Email
	@NotBlank
	private String email;

	@Size(min = 4)
	@NotBlank
	private String password;

	@Column(nullable = false)
	private String role;

	private String profilePictureUrl;

	@CreationTimestamp
	private LocalDateTime accountCreatedAt;

	@UpdateTimestamp
	private LocalDateTime accountUpdatedAt;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"user"})
	private Student student;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"user", "department"})
	private Instructor instructor;
}
