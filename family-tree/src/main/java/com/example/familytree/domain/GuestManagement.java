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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Domain Bảng quản lý khách mời
 * @author nga
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "guest_management")
public class GuestManagement {
  @Schema(description = "id quản lý khách mời")
  @Id
  @SequenceGenerator(
      name = "guest_management_seq",
      sequenceName = "guest_management_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guest_management_seq")
  @Column(name = "id")
  private Long id;

  @Schema(description = "id của thành viên")
  @Column(name = "member_id")
  private Long memberId;

  @Schema(description = "id quản lý sự kiện")
  @Column(name = "event_management_id")
  private Long eventManagementId;


}
