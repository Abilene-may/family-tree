package com.example.familytree.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO báo cáo thu chi từ ngày đến ngày
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ReAndExReport {
  @Schema(description = "Số dư kỳ trước")
  private Long previousBalance;

  @Schema(description = "Tổng tiền thu theo định mức")
  private Long totalRevenue;
  @Schema(description = "Tổng tiền tài trợ")
  private Long totalSposorship;

  @Schema(description = "Tổng tiền chi")
  private Long totalExpense;

  @Schema(description = "Số tiền còn lại")
  private Long remainingBalance;
}
