package com.example.familytree.repository;

import com.example.familytree.domain.ExpenseDetail;
import com.example.familytree.domain.ExpenseManagement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseDetailRepository extends JpaRepository<ExpenseDetail, Long>,
    JpaSpecificationExecutor<ExpenseDetail> {

  List<ExpenseDetail> findAllByExpenseManagementId(Long expenseManagementId);

  @Query(
      value =
          "SELECT *\n"
              + "FROM expense_detail\n"
              + "WHERE (date_of_pay <= :effectiveEndDate\n"
              + "    AND date_of_pay >= :effectiveStartDate)\n"
              + "order by date_of_pay ASC",
      nativeQuery = true)
  List<ExpenseDetail> findAllByStartDateAndEndDate(
      LocalDate effectiveStartDate, LocalDate effectiveEndDate);

  @Query(
      value =
          "SELECT *\n"
              + "FROM expense_detail\n"
              + "WHERE (date_of_pay >= :effectiveStartDate)\n"
              + "order by date_of_pay ASC",
      nativeQuery = true)
  List<ExpenseDetail> findAllByStartDate(LocalDate effectiveStartDate);

  @Query(
      value =
          "SELECT *\n"
              + "FROM expense_detail\n"
              + "WHERE (date_of_pay <= :effectiveEndDate)\n"
              + "order by date_of_pay ASC",
      nativeQuery = true)
  List<ExpenseDetail> findAllByEndDate(LocalDate effectiveEndDate);
}
