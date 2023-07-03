package com.example.familytree.models.membermanagement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO request đăng ký tài khoản
 * @author nga
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignUpReqDTO {
  @Schema(description = "id của thành viên")
  private Long memberId;

  @Schema(description = "username của người dùng")
  private String userName;

  @Schema(description = "password của người dùng")
  private String password;

  @Schema(description = "role của người dùng")
  private String role;
}
