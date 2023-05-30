package com.example.familytree.models;

import com.example.familytree.domain.FinancialSponsorship;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* Bảng báo cáo quản lý tiền tài trợ
    * @author nga
    * @since 30/07/2023
    */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FinancialSponsorshipReport {
  @Schema(description = "Tổng tiền tài trợ")
  private Long totalMoney;

  @Schema(description = "danh sách các khoản tài trợ của năm")
  private List<FinancialSponsorship> financialSponsorships;
}
