package com.example.QuizApp.Service;

import com.example.QuizApp.DOA.QuestionRepo;
import com.example.QuizApp.DOA.QuizRepo;
import com.example.QuizApp.DTO.ResponseQuestionDTO;
import com.example.QuizApp.DTO.ResponseQuizDTO;
import com.example.QuizApp.Model.Question;
import com.example.QuizApp.Model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuestionRepo questionRepo;

    public Quiz createQuiz(String category, int numques, String title) {
        List<Question> questions = questionRepo.findRandomQuestionsByCategory(category,numques);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        Quiz quiz1 = quizRepo.save(quiz);
        return quiz1;
    }

    public List<ResponseQuestionDTO> getQuizQuestions(int quizId) {
        Optional<Quiz> quiz = quizRepo.findById(quizId);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<ResponseQuestionDTO> questionsForUser = new ArrayList<>();
        for (Question q: questionsFromDB){
            ResponseQuestionDTO responseQuestionDTO = new ResponseQuestionDTO(q.getQuestionId(),q.getCategory(),q.getDifficultyLevel(),q.getQuestion(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsForUser.add(responseQuestionDTO);
        }
        return questionsForUser;

    }

    public Integer calculateResult(int quizId, List<ResponseQuizDTO> responseQuizDTOS) {
        Optional<Quiz> quiz = quizRepo.findById(quizId);
        List<Question> questions = quiz.get().getQuestions();
        int result = 0;
        int i = 0;
        for(ResponseQuizDTO q: responseQuizDTOS){
            if(q.getResponse().equals(questions.get(i).getCorrectAnswer())){
                result++;
                i++;

            }
            else {
                i++;
            }
        }

        return result;

    }
}
