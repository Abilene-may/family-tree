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
@Table(name = "revenue_management")
public class RevenueManagement {
  @Schema(description = "id của quản lý thu")
  @Id
  @SequenceGenerator(name = "revenue_seq", sequenceName = "revenue_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revenue_seq")
  @Column(name = "id")
  private Long id;

  @Schema(description = "Năm quản lý thu")
  @Column(name = "year")
  private Integer year;

  @Schema(description = "Số tiền thu dự tính")
  @Column(name = "estimated_revenue")
  private Long estimatedRevenue;

  @Schema(description = "Thực thu")
  @Column(name = "real_revenue")
  private Long realRevenue;

  @Schema(description = "Trạng thái thu")
  @Column(name = "status")
  private String status;

  @Schema(description = "Trạng thái thu search")
  @Column(name = "status_search")
  private String statusSearch;

  @Schema(description = "Hạn thu")
  @Column(name = "due_date")
  private LocalDate dueDate;
}
