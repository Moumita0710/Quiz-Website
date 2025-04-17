package com.example.Quiz_Project.dto;

import lombok.Data;

@Data
public class QuestionResponse {
    private Long questionId;
    private String selectedOption;
}
