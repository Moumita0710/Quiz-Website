package com.example.Quiz_Project.repository;

import com.example.Quiz_Project.entities.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TestResultRepository extends JpaRepository<TestResult,Long> {
    List<TestResult> findAllByUserId(Long userId);
}
