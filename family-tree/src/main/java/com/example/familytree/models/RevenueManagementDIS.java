package com.example.familytree.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO update khoản thu hằng năm
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RevenueManagementDIS {
  @Schema(description = "id của hệ thống")
  private Long id;
  @Schema(description = "Mức thu / 1 người")
  private Long revenuePerPerson;
}
