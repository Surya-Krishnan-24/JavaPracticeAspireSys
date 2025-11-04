package com.example.CollegeCoursesManagement.Model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long departmentId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String deptCode;

	@CreationTimestamp
	private LocalDateTime departmentCreatedAt;

	@UpdateTimestamp
	private LocalDateTime departmentUpdatedAt;

}
