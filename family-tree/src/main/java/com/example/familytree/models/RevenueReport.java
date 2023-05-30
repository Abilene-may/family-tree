package com.example.familytree.models;

import com.example.familytree.domain.RevenueManagement;
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
  @Schema(description = "Tổng thu của năm")
  private Long totalRevenue;
  @Schema(description = "danh sách các khoản thu theo năm")
  private List<RevenueManagement> revenueManagements;
}
