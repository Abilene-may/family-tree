package com.example.familytree.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO báo cáo thu chi
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ReAndExReport {
  @Schema(description = "Năm báo cáo thu-chi")
  private Integer year;

  @Schema(description = "Tổng tiền thu")
  private Long totalRevenue;

  @Schema(description = "Tổng tiền chi")
  private Long totalExpense;

  @Schema(description = "Số dư còn lại")
  private Long remainingBalance;
}
