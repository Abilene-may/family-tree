package com.example.familytree.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO thống kê đời
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class GenerationDTO {
  @Schema(description = "Thế hệ của thành viên")
  private Integer generation;

  @Schema(description = "Số lượng của thế hệ")
  private Integer count;
}
