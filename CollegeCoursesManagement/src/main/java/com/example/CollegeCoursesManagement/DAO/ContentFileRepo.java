package com.example.CollegeCoursesManagement.DAO;

import com.example.CollegeCoursesManagement.Model.ContentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentFileRepo extends JpaRepository<ContentFile, Long> {
	
}
