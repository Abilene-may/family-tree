package com.example.familytree.repository;

import com.example.familytree.domain.ExpenseManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpenseManagementRepository extends JpaRepository<ExpenseManagement, Long>,
    JpaSpecificationExecutor<ExpenseManagement> {}
