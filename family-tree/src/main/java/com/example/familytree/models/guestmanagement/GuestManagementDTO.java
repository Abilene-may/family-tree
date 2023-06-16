package com.example.familytree.models.guestmanagement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO các khách mời
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class GuestManagementDTO {

  @Schema(description = "id thành viên")
  private Long memberId;

  @Schema(description = "họ và tên thành viên")
  private String fullName;

  @Schema(description = "Giới tính nam/nữ")
  private String gender;


  @Schema(description = "Ngày sinh")
  private LocalDate dateOfBirth;

  @Schema(description = "Số điện thoại di động")
  private String mobilePhoneNumber;

  @Schema(description = "Nghề nghiệp")
  private String career;

  @Schema(description = "id của quản lý sự kiện")
  private Long eventManagementId;
}
