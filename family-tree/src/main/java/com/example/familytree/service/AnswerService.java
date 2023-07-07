package com.example.familytree.service;

import com.example.familytree.domain.Answer;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.answer.AnswerDTO;
import com.example.familytree.models.answer.AnswerReqDTO;

public interface AnswerService {
  AnswerDTO create(AnswerReqDTO reqDTO) throws FamilyTreeException;
  AnswerDTO getByQuestionId(Long questionId) throws FamilyTreeException;
}
