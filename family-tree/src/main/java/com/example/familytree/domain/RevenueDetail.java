package com.example.familytree.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Danh sách danh thu năm
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "revenue_detail")
public class RevenueDetail {
  @Id
  @Schema(name = "id của giao dịch thu = id của thành viên")
  private Long id;

  @Schema(description = "Năm quản lý thu")
  @Column(name = "year")
  private Integer year;

  @Schema(description = "Người đóng tiền")
  @Column(name = "payer")
  private String payer;

  @Schema(description = "Số tiền đóng")
  @Column(name = "money")
  private Long money;

  @Schema(description = "Ngày đóng")
  @Column(name = "date")
  private LocalDate date;

  @Schema(description = "Trạng thái đã thu / chưa thu")
  @Column(name = "status")
  private Boolean status;

  @Schema(description = "id của quản lý thu revenue management")
  @Column(name = "id_revenue_management")
  private Long idRevenueManagement;
}
