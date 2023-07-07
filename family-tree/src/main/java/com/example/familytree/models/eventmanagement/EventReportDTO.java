package com.example.familytree.models.eventmanagement;

import com.example.familytree.domain.ExpenseDetail;
import com.example.familytree.domain.RevenueDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO thông tin của báo cáo sự kiện
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class EventReportDTO {
  @Schema(description = "Tổng số tiền đã chi cho các sự kiện trong khoảng time này")
  private Long totalEvent;
  @Schema(description = "danh sách các khoản chi cho sự kiện ")
  private List<ExpenseDetail> expenseDetails;
}
