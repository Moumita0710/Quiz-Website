package com.example.Quiz_Project.dto;

import lombok.Data;
import java.util.List;
@Data
public class TestDetailsDTO {
    private TestDTO testDTO;
    private List<QuestionDTO> questions;


}
