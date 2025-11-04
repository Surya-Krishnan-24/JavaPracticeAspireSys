package com.example.CollegeCoursesManagement.DAO;

import com.example.CollegeCoursesManagement.Model.Subtopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtopicRepo extends JpaRepository<Subtopic, Long> {
	
}
