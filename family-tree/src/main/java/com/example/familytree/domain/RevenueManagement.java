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
  * Quản lý thu
   */
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

  @Schema(description = "Tên khoản thu")
  @Column(name = "revenue_name")
  private String revenueName;

  @Schema(description = "Mức thu / 1 người")
  @Column(name = "revenue_per_person")
  private Long revenuePerPerson;

  @Schema(description = "Trạng thái thu")
  @Column(name = "status")
  private String status;

  @Schema(description = "Ngày bắt đầu thu")
  @Column(name = "start_date")
  private LocalDate startDate;

  @Schema(description = "Ngày hết hạn thu")
  @Column(name = "due_date")
  private LocalDate dueDate;
}
