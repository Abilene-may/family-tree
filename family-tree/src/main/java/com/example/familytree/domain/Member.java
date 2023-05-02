package com.example.familytree.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
/**
 * Domain Bảng thành viên trong gia phả
 *
 * @author nga
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {
  @Schema(description = "id thành viên")
  @Id
  @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
  @Column(name = "id")
  private Long id;

  @Schema(description = "họ và tên thành viên")
  @Column(name = "full_name")
  private String fullName;

  @Schema(description = "Giới tính nam/nữ")
  @Column(name = "gender")
  private String gender;

  @Schema(description = "Ngày sinh")
  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Schema(description = "Số điện thoại di động")
  @Column(name = "mobile_phone_number")
  private String mobilePhoneNumber;

  @Schema(description = "Nghề nghiệp")
  @Column(name = "career")
  private String career;

  @Schema(description = "Học vấn")
  @Column(name = "education")
  private String education;

  @Schema(description = "Họ tên bố của thành viên")
  @Column(name = "name_dad")
  private String nameDad;

  @Schema(description = "Họ tên mẹ của thành viên")
  @Column(name = "name_mom")
  private String nameMom;

  @Schema(description = "Tình trạng hôn nhân")
  @Column(name = "marital_status")
  private String maritalStatus;

  @Schema(description = "Tên vợ/chồng của thành viên nếu có")
  @Column(name = "name_wife_or_husband")
  private String nameWifeOrHusband;

  @Schema(description = "vai trò của thành viên trong gia phả")
  @Column(name = "role")
  private String role;

  @Schema(description = "Trạng thái đã mất hay chưa")
  @Column(name = "status")
  private String status;

  @Schema(description = "Ngày mất")
  @Column(name = "date_of_death")
  private LocalDate dateOfDeath;

  @Schema(description = "Nơi an táng")
  @Column(name = "burial_place")
  private String burialPlace;

  @Schema(description = "id của user")
  @Column(name = "user_id")
  private Long userId;

  @Schema(description = "quyền có thể sửa")
  @Column(name = "canEdit")
  private Boolean canEdit;

  @Schema(description = "Quyền có thể thêm")
  @Column(name = "canAdd")
  private Boolean canAdd;

  @Schema(description = "Quyền có thể xem")
  @Column(name = "canView")
  private Boolean canView;

}
