package com.example.familytree.repository;

import com.example.familytree.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionRepository extends JpaRepository<Question, Long>,
    JpaSpecificationExecutor<Question> {

}
