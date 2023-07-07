package com.example.familytree.service;

import com.example.familytree.domain.Question;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.question.QuestionReqDTO;

public interface QuestionService {
  Question create(QuestionReqDTO reqDTO) throws FamilyTreeException;
  Question update(Question question) throws FamilyTreeException;
  Question getById(Long id) throws FamilyTreeException;
  void delete(Long id) throws FamilyTreeException;
}
