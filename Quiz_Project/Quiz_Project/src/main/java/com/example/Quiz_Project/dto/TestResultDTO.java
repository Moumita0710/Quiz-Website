package com.example.Quiz_Project.dto;

import lombok.Data;

@Data
public class TestResultDTO {
    private Long id;
    private int totalQuestions;
    private int correctAnswer;
    private double percentage;
    private String testName;
    private String userName;
}
