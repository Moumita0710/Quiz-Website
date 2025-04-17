package com.example.Quiz_Project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubmitTestDTO {
    @JsonProperty("testId")
    private Long testID;
    @JsonProperty("userId")
    private Long userID;
    private List<QuestionResponse> responses;


}
