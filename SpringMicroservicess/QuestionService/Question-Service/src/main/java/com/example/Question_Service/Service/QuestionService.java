package com.example.Question_Service.Service;

import com.example.Question_Service.DTO.ResponseQuestionDTO;
import com.example.Question_Service.DTO.ResponseQuizDTO;
import com.example.Question_Service.Model.Question;
import com.example.Question_Service.DOA.QuestionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<List<Integer>> getQuestionsForQiz(String category, int numOfQuestion) {
        List<Integer> questions = questionRepo.findRandomQuestionsByCategory(category,numOfQuestion);
        return new ResponseEntity<>(questions, HttpStatus.OK);

    }

    public ResponseEntity<List<ResponseQuestionDTO>> getQuestionsByIds(List<Integer> questionIds) {
        List<ResponseQuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> question = questionRepo.findAllById(questionIds);

        for (Question q: question){
            ResponseQuestionDTO responseQuestionDTO = new ResponseQuestionDTO();
            responseQuestionDTO.setQuestionId(q.getQuestionId());
            responseQuestionDTO.setCategory(q.getCategory());
            responseQuestionDTO.setDifficultyLevel(q.getDifficultyLevel());
            responseQuestionDTO.setQuestion(q.getQuestion());
            responseQuestionDTO.setOption1(q.getOption1());
            responseQuestionDTO.setOption2(q.getOption2());
            responseQuestionDTO.setOption3(q.getOption3());
            responseQuestionDTO.setOption4(q.getOption4());

            questionDTOS.add(responseQuestionDTO);
        }
        return new ResponseEntity<>(questionDTOS,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateScore(List<ResponseQuizDTO> responseQuizDTOS) {


        int result = 0;

        for(ResponseQuizDTO q: responseQuizDTOS){
            Optional<Question> question = questionRepo.findById(q.getId());

            if(question.get().getCorrectAnswer().equals(q.getResponse())){
                result++;

            }

        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
