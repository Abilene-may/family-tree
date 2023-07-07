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
@Table(name = "question")
public class Question {

  @Schema(description = "id quản lý yêu cầu")
  @Id
  @SequenceGenerator(
      name = "question_seq",
      sequenceName = "question_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
  @Column(name = "id")
  private Long id;

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

  @Schema(description = "Ngày gửi yêu cầu")
  @Column(name = "send_date")
  private LocalDate sendDate;

  @Schema(description = "Trạng thái của yêu cầu")
  @Column(name = "status")
  private String status;
}
