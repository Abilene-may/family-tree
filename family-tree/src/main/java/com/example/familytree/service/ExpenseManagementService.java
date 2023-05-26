package com.example.familytree.service;

import com.example.familytree.domain.ExpenseManagement;
import com.example.familytree.exceptions.FamilyTreeException;
import java.util.List;

public interface ExpenseManagementService {
  ExpenseManagement create(ExpenseManagement expenseManagement) throws FamilyTreeException;
  List<ExpenseManagement> getAll() throws FamilyTreeException;
  ExpenseManagement getById(Long id) throws FamilyTreeException;
  void update(ExpenseManagement expenseManagement) throws FamilyTreeException;
}
