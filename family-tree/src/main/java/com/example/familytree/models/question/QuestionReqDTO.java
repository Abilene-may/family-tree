package com.example.familytree.models.question;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO các yêu cầu đầu của câu hỏi
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class QuestionReqDTO {
  @Schema(description = "Tiêu đề yêu cầu")
  @Column(name = "title")
  private String title;

  @Schema(description = "Lý do gửi yêu cầu")
  @Column(name = "reason")
  private String reason;


  @Schema(description = "Nội dung của yêu cầu")
  @Column(name = "content")
  private String content;

  @Schema(description = "id của thành viên")
  @Column(name = "member_id")
  private Long memberId;
}
