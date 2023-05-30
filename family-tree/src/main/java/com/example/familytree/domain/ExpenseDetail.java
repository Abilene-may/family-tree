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

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "expense_detail")
public class ExpenseDetail {

  @Id
  @Schema(name = "id của quản giao dịch chi")
  @SequenceGenerator(
      name = "expense_detail_seq",
      sequenceName = "expense_detail_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_detail_seq")
  private Long id;

  @Schema(description = "Năm quản lý chi")
  @Column(name = "year")
  private Integer year;

  @Schema(description = "Tên khoản chi/ giao dịch chi")
  @Column(name = "expense_name")
  private String expenseName;

  @Schema(description = "Người nhận")
  @Column(name = "receiver")
  private String receiver;

  @Schema(description = "Ngày thanh toán")
  @Column(name = "date_of_pay")
  private LocalDate dateOfPay;

  @Schema(description = "Số tiền chi")
  @Column(name = "expense_money")
  private Long expenseMoney;

  @Schema(description = "Ghi chú")
  @Column(name = "note")
  private String note;

  @Schema(description = "id của quản lý thu")
  @Column(name = "expense_management_id")
  private Long expenseManagementId;
}
