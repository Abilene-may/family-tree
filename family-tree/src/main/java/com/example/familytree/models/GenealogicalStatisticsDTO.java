package com.example.familytree.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Bảng thống kê gia phả
 * @author nga
 * @since 27/07/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class GenealogicalStatisticsDTO {
  @Schema(description = "Số thành viên nam")
  private int numberOfMale;

  @Schema(description = "Số thành viên nữ")
  private int numberOfFemale;

  @Schema(description = "Số thành viên từ 18 đến 60 tuổi")
  private int ageInTheRange;

  @Schema(description = "Tổng số thành viên trong gia phả")
  private int totalMember;

  @Schema(description = "Tuổi thọ TB của nữ trong gia phả")
  private int averageAgeOfFemale;

  @Schema(description = "Tuổi thọ TB của nam trong gia phả")
  private int averageAgeOfMale;

  @Schema(description = "Thống kê theo thế hệ")
  private List<GenerationDTO> generationDTOS;
}
