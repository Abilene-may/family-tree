package com.example.familytree.models;

import io.swagger.v3.oas.annotations.media.Schema;import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDTO implements Serializable {
  @Schema(description = "user name của thành viên")
  private String userName;

  @Schema(description = "password")
  private String password;

  @Schema(description = "vai trò của user")
  private String role;
}
