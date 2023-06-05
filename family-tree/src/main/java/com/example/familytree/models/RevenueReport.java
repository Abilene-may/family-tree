package com.example.familytree.models;

import com.example.familytree.domain.RevenueDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RevenueReport {
  @Schema(description = "Tổng số tiền đã thu")
  private Long totalRevenue;
  @Schema(description = "danh sách các khoản thu từ ngày đến ngày")
  private List<RevenueDetail> revenueDetails;
}
