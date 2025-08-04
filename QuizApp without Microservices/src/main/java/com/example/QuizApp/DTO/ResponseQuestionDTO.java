package com.example.QuizApp.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseQuestionDTO {

    private int questionId;
    private String category;
    private String difficultyLevel;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}
