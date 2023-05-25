package com.example.familytree.repository;

import com.example.familytree.domain.ExpenseManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpenseManagementRepositoryy extends JpaRepository<ExpenseManagement, Long>,
    JpaSpecificationExecutor<ExpenseManagement> {}
