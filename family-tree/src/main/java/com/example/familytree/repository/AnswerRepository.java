package com.example.familytree.repository;

import com.example.familytree.domain.Answer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnswerRepository extends JpaRepository<Answer, Long>,
    JpaSpecificationExecutor<Answer> {

  Optional<Answer> findByQuestionId(Long questionId);
}
