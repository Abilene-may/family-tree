package com.example.familytree.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Danh sách danh mục thu theo năm
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
  @Schema(name = "id của danh mục")
  @SequenceGenerator(
      name = "revenue_detail_seq",
      sequenceName = "revenue_detail_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revenue_detail_seq")
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

  @Schema(description = "Loại thu")
  @Column(name = "type_of_revenue")
  private String typeOfRevenue;

  @Schema(description = "Loại thu search")
  @Column(name = "type_of_revenue_search")
  private String typeOfRevenueSearch;

  @Schema(description = "Ngày đóng")
  @Column(name = "date")
  private LocalDate date;

  @Schema(description = "id của quản lý thu revenue management")
  @Column(name = "id_revenue_management")
  private Long idRevenueManagement;
}
