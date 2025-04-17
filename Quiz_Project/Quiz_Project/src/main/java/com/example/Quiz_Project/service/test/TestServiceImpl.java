package com.example.Quiz_Project.service.test;

import com.example.Quiz_Project.dto.*;
import com.example.Quiz_Project.entities.Question;
import com.example.Quiz_Project.entities.Test;
import com.example.Quiz_Project.entities.TestResult;
import com.example.Quiz_Project.entities.User;
import com.example.Quiz_Project.repository.QuestionRepository;
import com.example.Quiz_Project.repository.TestRepository;
import com.example.Quiz_Project.repository.TestResultRepository;
import com.example.Quiz_Project.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private UserRepository userRepository;

    public TestDTO createTest(TestDTO dto) {
        Test test = new Test();
        test.setTitle(dto.getTitle());
        test.setDescription(dto.getDescription());
        test.setTime(dto.getTime());
        return testRepository.save(test).getDto();
    }

    public QuestionDTO addQuestionInTest(QuestionDTO dto) {
        Optional<Test> optionalTest = testRepository.findById(dto.getId());
        if (optionalTest.isPresent()) {
            Question question = new Question();

            question.setTest(optionalTest.get());
            question.setQuestionText(dto.getQuestionText());
            question.setOptionA(dto.getOptionA());
            question.setOptionB(dto.getOptionB());
            question.setOptionC(dto.getOptionC());
            question.setOptionD(dto.getOptionD());
            question.setCorrectOption(dto.getCorrectOption());

            return questionRepository.save(question).getDto();

        }
        throw new EntityNotFoundException("Test No Found");
    }

    public List<TestDTO> getAllTests() {
        return testRepository.findAll().stream().peek(
                        test -> test.setTime(test.getQuestions().size() * test.getTime())).collect(Collectors.toList())
                .stream().map(Test::getDto).collect(Collectors.toList());
    }

    public TestDetailsDTO getAllQuestionsByTest(Long id) {
        Optional<Test> optionalTest = testRepository.findById(id);
        TestDetailsDTO testDetailsDTO = new TestDetailsDTO();
        if (optionalTest.isPresent()) {
            TestDTO testDto = optionalTest.get().getDto();
            testDto.setTime(optionalTest.get().getTime() * optionalTest.get().getQuestions().size());

            testDetailsDTO.setTestDTO(testDto);
            testDetailsDTO.setQuestions(optionalTest.get().getQuestions().stream().map(Question::getDto).toList());
            return testDetailsDTO;
        }
        return testDetailsDTO;
    }
    public TestResultDTO submitTest(SubmitTestDTO request){
        Test test=testRepository.findById(request.getTestID()).orElseThrow(() -> new EntityNotFoundException("Test not found"));
        User user=userRepository.findById(request.getUserID()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        int correctAnswer=0;
        for(QuestionResponse response:request.getResponses()){
            Question question=questionRepository.findById(response.getQuestionId())
                    .orElseThrow(() -> new EntityNotFoundException("Question not found"));
            if(question.getCorrectOption().equals(response.getSelectedOption())){
                correctAnswer++;
            }
        }
        int totalQuestion=test.getQuestions().size();
        double percentage=((double) correctAnswer/totalQuestion)*100;
        TestResult testResult=new TestResult();
        testResult.setTest(test);
        testResult.setUser(user);
        testResult.setTotalQuestions(totalQuestion);
        testResult.setCorrectAnswer(correctAnswer);
        testResult.setPercentage(percentage);

        return testResultRepository.save(testResult).getDto();
    }
    public List<TestResultDTO> getAllTestResults(){
        return testResultRepository.findAll().stream().map(TestResult::getDto).collect(Collectors.toList());
    }
    public List<TestResultDTO> getAllTestResultsOfUser(Long userId){
        return testResultRepository.findAllByUserId(userId).stream().map(TestResult::getDto).collect(Collectors.toList());
    }
}
