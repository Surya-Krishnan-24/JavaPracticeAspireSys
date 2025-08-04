package com.example.Question_Service.Controller;


import com.example.Question_Service.DTO.ResponseQuestionDTO;
import com.example.Question_Service.DTO.ResponseQuizDTO;
import com.example.Question_Service.Model.Question;
import com.example.Question_Service.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionsController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    Environment environment;


    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> allQuestions = questionService.getAllQuestions();
        return new ResponseEntity<>(allQuestions, HttpStatus.OK);
    }

    @GetMapping("allQuestions/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category){
        List<Question> QuestionByCategory = questionService.getQuestionByCategory(category);
        return new ResponseEntity<>(QuestionByCategory, HttpStatus.OK);
    }

    @PostMapping("addQuestions")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question){
        Question addedQuestion = questionService.addOrUpdateQuestion(question);
        return new ResponseEntity<>(addedQuestion,HttpStatus.CREATED);
    }

    @PutMapping("updateQuestion")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question){
        Question updatedQuestion = questionService.addOrUpdateQuestion(question);
        return new ResponseEntity<>(updatedQuestion,HttpStatus.OK);
    }

    @DeleteMapping("deleteQuestion/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("id") int questionId){
        questionService.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionForQuiz(@RequestParam String category,@RequestParam int numOfQuestion ){
        return questionService.getQuestionsForQiz(category,numOfQuestion);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<ResponseQuestionDTO>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsByIds(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<ResponseQuizDTO> responseQuizDTOS){
        return questionService.calculateScore(responseQuizDTOS);
    }

}
