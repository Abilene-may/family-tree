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
import lombok.Builder;
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
@Builder
@Entity
@Table(name = "members")
public class Member {
  @Schema(description = "id thành viên")
  @Id
  @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
  @Column(name = "id")
  private Long id;

  @Schema(description = "Thế hệ của thành viên")
  @Column(name = "generation")
  private Integer generation;

  @Schema(description = "họ và tên thành viên")
  @Column(name = "full_name")
  private String fullName;

  @Schema(description = "Họ và tên thành viên dạng search")
  @Column(name = "name_search")
  private String nameSearch;

  @Schema(description = "Giới tính nam/nữ")
  @Column(name = "gender")
  private String gender;

  @Schema(description = "Giới tính nam/nữ dạng tìm kiếm")
  @Column(name = "gender_search")
  private String genderSearch;

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

  @Schema(description = "Id của bố")
  @Column(name = "dad_id")
  private Long dadId;

  @Schema(description = "Id của mẹ")
  @Column(name = "mom_id")
  private Long momId;

  @Schema(description = "Tình trạng hôn nhân")
  @Column(name = "marital_status")
  private String maritalStatus;

  @Schema(description = "Tình trạng hôn nhân dạng search - tiếng việt ko dấu")
  @Column(name = "marital_search")
  private String maritalSearch;

  @Schema(description = "id của vợ/chồng nếu đã kết hôn")
  @Column(name = "partner_id")
  private Long partnerId;

  @Schema(description = "vai trò của thành viên trong gia phả")
  @Column(name = "role")
  private String role;

  @Schema(description = "Vai trò của thành viên dạng search")     //không dấu
  @Column(name = "role_search")
  private String roleSearch;

  @Schema(description = "Trạng thái đã mất hay chưa")
  @Column(name = "status")
  private String status;

  @Schema(description = "Ngày mất")
  @Column(name = "date_of_death")
  private LocalDate dateOfDeath;

  @Schema(description = "Nơi an táng")
  @Column(name = "burial_place")
  private String burialPlace;

  @Schema(description = "username của người dùng")
  @Column(name = "username")
  private String userName;

  @Schema(description = "password của người dùng")
  @Column(name = "password")
  private String password;

}
