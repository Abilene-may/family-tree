package com.example.familytree.models;

import com.example.familytree.domain.Member;import io.swagger.v3.oas.annotations.media.Schema;import jakarta.persistence.Column;import java.time.LocalDate;import java.util.List;import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MemberDTO {
  @Schema(description = "Số thành viên trong gia phả")
  private Integer count;
  @Schema(description = "Danh sách các thành viên")
  private List<Member> members;
}
