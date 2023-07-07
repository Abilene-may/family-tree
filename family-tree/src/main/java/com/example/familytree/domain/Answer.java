package com.example.familytree.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Domain Bảng phản hồi của quản lý
 *
 * @author nga
 * @since 06/07/2023
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "answer")
public class Answer {
  @Schema(description = "id quản lý phản hồi/ câu trả lời")
  @Id
  @SequenceGenerator(
      name = "answer_seq",
      sequenceName = "answer_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_seq")
  @Column(name = "id")
  private Long id;

  @Schema(description = "Nội dung phản hồi")
  @Column(name = "content")
  private String content;

  @Schema(description = "Ngày gửi phản hồi")
  @Column(name = "send_date")
  private LocalDate sendDate;

  @Schema(description = "id của yêu cầu/ câu hỏi")
  @Column(name = "question_id")
  private Long questionId;

  @Schema(description = "id của thành viên")
  @Column(name = "member_id")
  private Long memberId;

  @Schema(description = "Người phản hồi yêu cầu")
  @Column(name = "responders_id")
  private Long respondersId;

}
