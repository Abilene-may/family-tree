package com.example.familytree.repository;

import com.example.familytree.domain.ExpenseDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpenseDetailRepository extends JpaRepository<ExpenseDetail, Long>,
    JpaSpecificationExecutor<ExpenseDetail> {

  List<ExpenseDetail> findAllByExpenseManagementId(Long expenseManagementId);
}
