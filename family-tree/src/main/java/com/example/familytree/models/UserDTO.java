package com.example.familytree.models;

import com.example.familytree.domain.Member;import io.swagger.v3.oas.annotations.media.Schema;import java.util.List;import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
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
}
