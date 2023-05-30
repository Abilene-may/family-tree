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
 * Domain Bảng chi tiết quản lý tiền tài trợ
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
@Table(name = "sponsorship_detail")
public class SponsorsipDetail {
  @Schema(description = "id của quản lý tiền tài trợ")
  @Id
  @SequenceGenerator(
      name = "sponsorship_detail_seq",
      sequenceName = "sponsorship_detail_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sponsorship_detail_seq")
  @Column(name = "id")
  private Long id;

  @Schema(description = "Năm quản lý tiền tài trợ")
  @Column(name = "year")
  private Integer year;
  @Schema(description = "Tên nhà/người tài trợ")
  @Column(name = "sponsors_name")
  private String sponsorsName;

  @Schema(description = "Loại tài trợ cá nhân/tổ chức")
  @Column(name = "type_of_sponsorship")
  private String typeOfSponsorship;

  @Schema(description = "Tổ chức nếu có")
  @Column(name = "organization")
  private String organization;

  @Schema(description = "Ngày đóng góp")
  @Column(name = "date")
  private LocalDate date;

  @Schema(description = "Chú thích")
  @Column(name = "note")
  private String note;

  @Schema(description = "Số tiền tài trợ")
  @Column(name = "sponsorship_money")
  private Long sponsorshipMoney;

  @Schema(description = "id của danh mục tài trợ ")
  @Column(name = "financial_sponsorship_id")
  private Long financialSponsorshipId;
}
