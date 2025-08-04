package com.example.QuizApp.Service;

import com.example.QuizApp.DOA.QuestionRepo;
import com.example.QuizApp.Model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepo questionRepo;

    public List<Question> getAllQuestions(){
        return questionRepo.findAll();
    }

    public List<Question> getQuestionByCategory(String category) {
        return questionRepo.findByCategory(category);
    }

    public Question addOrUpdateQuestion(Question question) {
        Question savedQuestion = questionRepo.save(question);
        return savedQuestion;
    }


    public void deleteQuestion(int questionId) {
        questionRepo.deleteById(questionId);

    }
}
