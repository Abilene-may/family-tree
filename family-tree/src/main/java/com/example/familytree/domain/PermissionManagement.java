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
import lombok.extern.slf4j.Slf4j;

/**
 * Domain Bảng phân quyền
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
@Table(name = "permission_management")
public class PermissionManagement {
  @Schema(description = "id quản lý phân quyền")
  @Id
  @SequenceGenerator(
      name = "permission_management_seq",
      sequenceName = "permission_management_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_management_seq")
  @Column(name = "id")
  private Long id;

  @Schema(description = "Tên nhóm quyền")
  @Column(name = "permission_group_name")
  private String permissionGroupName;

  @Schema(description = "Mô tả nhóm quyền")
  @Column(name = "permission_description")
  private String permissionsDescription;

  // Các chức năng của quyền
  // Quản lý gia phả
  @Column(name = "view_members")
  private Boolean viewMebers;

  @Column(name = "create_members")
  private Boolean createMembers;

  @Column(name = "update_member")
  private Boolean updateMembers;

  // Quản lý tài chính
  @Column(name = "view_financial")
  private Boolean viewFinancial;

  @Column(name = "create_financial")
  private Boolean createFinancial;

  @Column(name = "update_financial")
  private Boolean updateFinancial;

  @Column(name = "delete_financial")
  private Boolean deleteFinancial;

  // Quản lý sự kiện
  @Column(name = "view_event")
  private Boolean viewEvent;

  @Column(name = "create_event")
  private Boolean createEvent;

  @Column(name = "update_event")
  private Boolean updateEvent;

  @Column(name = "delete_event")
  private Boolean deleteEvent;

}
