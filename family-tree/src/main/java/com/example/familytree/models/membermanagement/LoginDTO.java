package com.example.familytree.models.membermanagement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO login
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginDTO {
  @Schema(description = "id của thành viên")
  private Long memberId;

  @Schema(description = "Họ và tên của thành viên")
  private String fullName;

  @Schema(description = "username của người dùng")
  private String userName;

  @Schema(description = "password của người dùng")
  private String password;

  @Schema(description = "vai trò của thành viên trong gia phả")
  private String role;
  // Các chức năng của quyền
  // Quản lý gia phả
  private Boolean viewMembers;

  private Boolean createMembers;

  private Boolean updateMembers;

  // Quản lý tài chính
  private Boolean viewFinancial;

  private Boolean createFinancial;

  private Boolean updateFinancial;

  private Boolean deleteFinancial;

  // Quản lý sự kiện
  private Boolean viewEvent;

  private Boolean createEvent;

  private Boolean updateEvent;

  private Boolean deleteEvent;
}
