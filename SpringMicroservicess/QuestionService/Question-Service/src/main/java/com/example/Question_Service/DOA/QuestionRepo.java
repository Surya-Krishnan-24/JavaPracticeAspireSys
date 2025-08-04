package com.example.Question_Service.DOA;

import com.example.Question_Service.Model.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT q.question_id FROM question q WHERE q.category =:category ORDER BY RANDOM() LIMIT :numques",nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numques);
}
