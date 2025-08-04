package com.example.QuizService.Controller;


import com.example.QuizService.DTO.QuizDto;
import com.example.QuizService.DTO.ResponseQuestionDTO;
import com.example.QuizService.DTO.ResponseQuizDTO;
import com.example.QuizService.Model.Quiz;
import com.example.QuizService.Service.QuizService;
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
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        String savedQues = quizService.createQuiz(quizDto.getCategory(),quizDto.getNumOfQues(),quizDto.getTitle());
        return new ResponseEntity<>(savedQues, HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<ResponseQuestionDTO>> getQuizQuestions(@PathVariable("id") int QuizId){
        List<ResponseQuestionDTO> responseQuestionDTOS = quizService.getQuizQuestions(QuizId);
        return new ResponseEntity<>(responseQuestionDTOS,HttpStatus.OK);

    }

    @PostMapping("submit")
    public ResponseEntity<Integer> submitQuiz(@RequestBody List<ResponseQuizDTO> responseQuizDTOS){
        ResponseEntity<Integer> result = quizService.calculateResult(responseQuizDTOS);
        return result;

    }
}
