package com.example.familytree.models.common;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorDTO implements Serializable {
  @Schema(description = "Mã lỗi")
  private String code;

  @Schema(description = "Thông tin lỗi")
  private String message;

  @Schema(description = "Danh sách thông tin lỗi")
  private List<String> messages;

  public ErrorDTO(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public ErrorDTO(String message) {
    this.message = message;
  }

  public ErrorDTO(String code, List<String> messages) {
    this.code = code;
    this.messages = messages;
  }
}
