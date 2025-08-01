package com.example.QuizApp.Controller;

import com.example.QuizApp.DTO.ResponseQuestionDTO;
import com.example.QuizApp.Model.Question;
import com.example.QuizApp.Model.Quiz;
import com.example.QuizApp.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<Quiz> createQuiz(@RequestParam String category, @RequestParam int numques,@RequestParam String title ){
        Quiz savedQues = quizService.createQuiz(category,numques,title);
        return new ResponseEntity<>(savedQues, HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<ResponseQuestionDTO>> getQuizQuestions(@PathVariable("id") int QuizId){
        List<ResponseQuestionDTO> responseQuestionDTOS = quizService.getQuizQuestions(QuizId);
        return new ResponseEntity<>(responseQuestionDTOS,HttpStatus.OK);

    }
}
