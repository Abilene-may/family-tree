package com.example.familytree.models;

import com.example.familytree.domain.SponsorsipDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
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
public class SponsorshipDetailDTO {
  @Schema(description = "Danh sách các giao dich tài trợ")
  private List<SponsorsipDetail> sponsorsipDetailList;

  @Schema(description = "Tổng tiền tài trợ")
  private Long totalMoney;
}
