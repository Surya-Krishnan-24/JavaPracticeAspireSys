package com.example.SpringDataJPA.repo;

import com.example.SpringDataJPA.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {

//    @Query("select s from Student s where s.Name = ?1")
//    List<Student> findByName(String name);

}
