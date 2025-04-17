package com.example.Quiz_Project.service.test;

import com.example.Quiz_Project.dto.*;
import com.example.Quiz_Project.entities.Test;

import java.util.List;

public interface TestService {
    TestDTO createTest(TestDTO dto);

    QuestionDTO addQuestionInTest(QuestionDTO dto);

    List<TestDTO> getAllTests();

    TestDetailsDTO getAllQuestionsByTest(Long id);

    TestResultDTO submitTest(SubmitTestDTO request);

    List<TestResultDTO> getAllTestResults();

    List<TestResultDTO> getAllTestResultsOfUser(Long userId);

}
