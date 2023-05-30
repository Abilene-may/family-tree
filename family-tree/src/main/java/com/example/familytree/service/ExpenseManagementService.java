package com.example.familytree.service;

import com.example.familytree.domain.ExpenseManagement;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.ExpenseReport;
import com.example.familytree.models.ReAndExReport;
import java.util.List;

public interface ExpenseManagementService {
 ExpenseManagement create(ExpenseManagement expenseManagement) throws FamilyTreeException;
 void update(ExpenseManagement expenseManagement) throws FamilyTreeException;
 List<ExpenseManagement> getAll() throws FamilyTreeException;
 ExpenseManagement getById(Long id) throws FamilyTreeException;
 ExpenseReport report(Integer year) throws FamilyTreeException;
 List<ReAndExReport> revenueAndExpenseReport() throws FamilyTreeException;
}
