package com.example.familytree.models.guestmanagement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO dữ liệu yêu cầu của thêm mới một khách mời
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class GuestReqCreate {
  @Schema(description = "id thành viên")
  private Long memberId;

  @Schema(description = "id sự kiện")
  private Long eventManagementId;
}
