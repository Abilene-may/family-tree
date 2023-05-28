package com.example.familytree.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import java.time.LocalDate;
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
public class RevenueDetailDTO {
  @Schema(description = "id của giao dịch thu")
  private Long id;
  @Schema(description = "Ngày đóng")
  private LocalDate date;

  @Schema(description = "Trạng thái đã thu / chưa thu")
  private Boolean status;
}
