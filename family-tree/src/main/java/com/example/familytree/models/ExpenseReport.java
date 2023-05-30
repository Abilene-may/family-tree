package com.example.familytree.models;

import com.example.familytree.domain.ExpenseManagement;
import com.example.familytree.domain.RevenueManagement;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO Báo cáo chi
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExpenseReport {

  @Schema(description = "Tổng chi của năm")
  private Long totalExpense;
  @Schema(description = "danh sách các khoản chi theo năm")
  private List<ExpenseManagement> expenseManagements;
}
