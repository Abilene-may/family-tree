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
import lombok.extern.slf4j.Slf4j;

/**
 * Domain Bảng quản lý tiền tài trợ
 *
 * @author nga
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "financial_sponsorship")
public class FinancialSponsorship {
  @Schema(description = "id của quản lý tiền tài trợ")
  @Id
  @SequenceGenerator(
      name = "financial_sponsorship_seq",
      sequenceName = "financial_sponsorship_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_sponsorship_seq")
  @Column(name = "id")
  private Long id;

  @Schema(description = "Năm quản lý tài trợ")
  @Column(name = "year")
  private Integer year;

  @Schema(description = "Tên khoản tài trợ")
  @Column(name = "financial_sponsorship_name")
  private String financialSponsorshipName;

  @Schema(description = "Ngày bắt đầu")
  @Column(name = "start_date")
  private LocalDate startDate;

  @Schema(description = "Ngày đóng")
  @Column(name = "end_date")
  private LocalDate endDate;

  @Schema(description = "Trạng thái quản lý tiền tài trợ")
  @Column(name = "status")
  private String status;

}
