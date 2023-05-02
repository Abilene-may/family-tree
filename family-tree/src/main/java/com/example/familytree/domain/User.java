package com.example.familytree.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Domain Bảng user người dùng
 *
 * @author nga
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user_member")
public class User {
  @Schema(description = "Id người dùng")
  @Id
  @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
  @Column(name = "id")
  private Long id;

  @Schema(description = "tên người dùng")
  @Column(name = "user_name")
  private String userName;

  /** Mật khẩu đăng nhập/đăng ký */
  @Schema(description = "Mật khẩu")
  @Column(name = "password")
  private String password;

  @Schema(description = "Phân quyền")
  @Column(name = "role")
  private String role;
}
