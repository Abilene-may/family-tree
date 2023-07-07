package com.example.familytree.models.answer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO các yêu cầu đầu vào của phản hồi
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AnswerReqDTO {

  @Schema(description = "id của câu hỏi/ yêu cầu")
  private Long questionId;

  @Schema(description = "Nội dung phản hồi")
  private String content;

  @Schema(description = "Id của người phản hồi")
  private Long respondersId;
  ;
}
