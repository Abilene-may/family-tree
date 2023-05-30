package com.example.familytree.service;

import com.example.familytree.domain.ExpenseDetail;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.ExpenseDetailDTO;

public interface ExpenseDetailService {
  ExpenseDetail create(ExpenseDetail expenseDetail) throws FamilyTreeException;
  void update(ExpenseDetail expenseDetail) throws FamilyTreeException;
  void delete(Long id) throws FamilyTreeException;
  ExpenseDetailDTO getAll(Long expenseManagementId) throws FamilyTreeException;
  ExpenseDetail getById(Long id) throws FamilyTreeException;
}
