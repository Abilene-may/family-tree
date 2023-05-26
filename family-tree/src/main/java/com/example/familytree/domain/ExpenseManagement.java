package com.example.familytree.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

  /*
  * Quản lý chi
   */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "expense_management")
public class ExpenseManagement {
  @Id
  @Schema(name = "id của quản lý chi")
  @SequenceGenerator(
      name = "expense_management_seq",
      sequenceName = "expense_management_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_management_seq")
  private Long id;

  @Schema(description = "Năm quản lý chi")
  @Column(name = "year")
  private Integer year;

  @Schema(description = "Tổng chi")
  @Column(name = "total_expense")
  private Long totalExpense;

  @Schema(description = "Trạng thái")
  @Column(name = "status")
  private String status;

    @Schema(description = "Trạng thái để tìm kiếm")
    @Column(name = "status-search")
    private String statusSearch;
}
