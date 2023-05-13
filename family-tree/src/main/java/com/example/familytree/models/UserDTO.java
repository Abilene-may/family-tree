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
  @Schema(description = "tên đăng nhập")
  private String userName;
  @Schema(description = "Mật khẩu")
  private String password;
}
