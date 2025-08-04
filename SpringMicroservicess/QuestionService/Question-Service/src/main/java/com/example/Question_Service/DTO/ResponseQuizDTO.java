package com.example.Question_Service.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseQuizDTO {
    private int id;
    private String response;
}
