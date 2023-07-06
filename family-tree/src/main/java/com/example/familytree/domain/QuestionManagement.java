package com.example.familytree.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Domain Bảng quản lý câu hỏi
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
@Table(name = "question_management")
public class QuestionManagement {
  @Schema(description = "id quản lý câu hỏi")
  @Id
  @SequenceGenerator(
      name = "question_management_seq",
      sequenceName = "question_management_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_management_seq")
  @Column(name = "id")
  private Long id;

  @Schema(description = "Tên nhóm quyền")
  @Column(name = "permission_group_name")
  private String permissionGroupName;

}
