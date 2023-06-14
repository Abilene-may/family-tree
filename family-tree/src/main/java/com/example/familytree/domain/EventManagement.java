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
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Domain Bảng quản lý sự kiện
 * @author nga
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "event_management")
public class EventManagement {
  @Schema(description = "id quản lý sự kiện")
  @Id
  @SequenceGenerator(
      name = "event_management_seq",
      sequenceName = "event_management_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_management_seq")
  @Column(name = "id")
  private Long id;

  @Schema(description = "Năm quản lý sự kiện")
  @Column(name = "year")
  private Integer year;

  @Schema(description = "Tên sự kiện")
  @Column(name = "revenue_name")
  private String revenueName;

  @Schema(description = "id của Người quản lý sự kiện")
  @Column(name = "member_id")
  private Long memberId;

  @Schema(description = "Người quản lý sự kiện")
  @Column(name = "event_manager")
  private String eventManager;

  // Khung giờ diễn ra sự kiện
  @Schema(description = "Bắt đầu từ")
  @Column(name = "start_time")
  private LocalTime startTime;

  @Schema(description = "Kết thúc lúc")
  @Column(name = "end_time")
  private LocalTime endTime;

  @Schema(description = "Ngày diễn ra sự kiện")
  @Column(name = "event_date")
  private LocalDate eventDate;

  @Schema(description = "Địa chỉ cụ thể số nhà/tên đường")
  @Column(name = "address")
  private String address;

  @Schema(description = "Loại sự kiện")
  @Column(name = "event_type")
  private String eventType;

  @Schema(description = "Ghi chú")
  @Column(name = "note")
  private String note;

  @Schema(description = "Trạng thái")
  @Column(name = "status")
  private String status;

  @Schema(description = "Những đầu mục chính của sự kiện")
  @Column(name = "content_event")
  private String contentEvent;
}
