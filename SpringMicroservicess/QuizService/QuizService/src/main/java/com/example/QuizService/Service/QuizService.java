package com.example.QuizService.Service;


import com.example.QuizService.DOA.QuizRepo;
import com.example.QuizService.DTO.ResponseQuestionDTO;
import com.example.QuizService.DTO.ResponseQuizDTO;
import com.example.QuizService.Feign.QuizInterface;
import com.example.QuizService.Model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuizInterface quizInterface;


    public String createQuiz(String category, int numques, String title) {
       List<Integer> questions = quizInterface.getQuestionForQuiz(category,numques).getBody();

       Quiz quiz = new Quiz();
       quiz.setTitle(title);
       quiz.setQuestionsIds(questions);
       quizRepo.save(quiz);
       return "Quiz Created Successfully";
    }

    public List<ResponseQuestionDTO> getQuizQuestions(Integer quizId) {
        Optional<Quiz> questionIds = quizRepo.findById(quizId);


        List<Integer> questionId = questionIds.get().getQuestionsIds();
        List<ResponseQuestionDTO> responseQuestionDTO = quizInterface.getQuestionsFromId(questionId).getBody();
        return responseQuestionDTO;
    }

    public ResponseEntity<Integer> calculateResult(List<ResponseQuizDTO> responseQuizDTOS) {
        ResponseEntity<Integer> result = quizInterface.getScore(responseQuizDTOS);
        return result;

    }
}
