package com.example.familytree.models.answer;

import com.example.familytree.domain.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO Thông tin của màn tạo phản hồi
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AnswerDTO {
  private Question question;

  @Schema(description = "Nội dung của phản hồi")
  private String contentResponse;

  @Schema(description = "Ngày gửi phản hồi")
  private LocalDate sendDateResponse;

  @Schema(description = "id của thành viên gửi phản hồi")
  private Long respondersId;
}
