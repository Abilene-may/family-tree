package com.example.familytree.models;

import com.example.familytree.domain.ExpenseDetail;
import com.example.familytree.domain.SponsorsipDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO chi tiết các giao dịch chi
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExpenseDetailDTO {
  @Schema(description = "Danh sách các giao dich chi")
  private List<ExpenseDetail> expenseDetailList;

  @Schema(description = "Tổng tiền tài trợ")
  private Long totalMoney;
}
