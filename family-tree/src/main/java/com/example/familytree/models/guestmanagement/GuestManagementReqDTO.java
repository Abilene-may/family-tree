package com.example.familytree.models.guestmanagement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO dữ liệu các yêu cầu đầu vào của khách mời
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class GuestManagementReqDTO {
  @Schema(description = "true chọn all thành viên, false chọn theo lựa chọn")
  private Boolean chooseAll;

  @Schema(description = "Lọc theo Giới tính")
  private String gender;

  // Từ tuổi đến tuổi
  @Schema(description = "Từ tuổi")
  private Integer startAge;

  @Schema(description = "Đến tuổi")
  private Integer endAge;

  @Schema(description = "id sự kiện")
  private Long eventManagementId;

}
