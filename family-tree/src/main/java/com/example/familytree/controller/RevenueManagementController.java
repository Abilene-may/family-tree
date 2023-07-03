package com.example.familytree.controller;

import com.example.familytree.domain.Member;
import com.example.familytree.domain.RevenueManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.RevenueManagementDIS;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.service.RevenueManagementService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/revenue-management")
public class RevenueManagementController {
  private final RevenueManagementService revenueManagementService;
  /**
   * Thêm mới một khoản thu hằng năm
   *
   * @author nga
   * @since 25/05/2023
   */
  @Schema(name = "create an annual revenue")
  @GetMapping("/create")
  public ResponseEntity<Object> create() {
    try {
      var management = revenueManagementService.create();
      return new ResponseEntity<>(management, HttpStatus.OK);
    } catch (FamilyTreeException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * API lấy tất cả các khỏan thu hàng năm
   *
   * @author nga
   * @since 25/05/2023
   */
  @Schema(name = "get all annual revenues")
  @GetMapping("/get-all")
  public ResponseEntity<Object> getAll() {
    try {
      var revenueManagements = revenueManagementService.getAllRevenue();
      return new ResponseEntity<>(revenueManagements, HttpStatus.OK);
    } catch (FamilyTreeException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * API tìm thông tin theo id
   *
   * @author nga
   * @since 25/05/2023
   */
  @Schema(name = "get imformation revenue by id")
  @GetMapping("/get-by-id/{id}")
  public ResponseEntity<Object> getById(@PathVariable Long id) {
    try {
      var revenueManagement = revenueManagementService.getById(id);
      return new ResponseEntity<>(revenueManagement, HttpStatus.OK);
    } catch (FamilyTreeException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  /**
   * API cập nhật thông tin khoản thu
   *
   * @author nga
   * @since 25/05/2023
   */
  @Schema(name = "update imformation revenue")
  @PutMapping("/update")
  public ResponseEntity<Object> update(@RequestBody RevenueManagementDIS revenueManagementDIS) {
    try {
      revenueManagementService.update(revenueManagementDIS);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (FamilyTreeException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  /**
   * API Xóa một quản lý thu
   *
   * @author nga
   * @since 06/06/2023
   */
  @Schema(name = "Xóa một quản lý thu")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    try {
      revenueManagementService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (FamilyTreeException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  /**
   * API Báo cáo khoản thu từ ngày đến ngày
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "Báo cáo khoản thu từ ngày đến ngày")
  @GetMapping("/report")
  public ResponseEntity<Object> report(@RequestParam(required = false) LocalDate effectiveStartDate,
      @RequestParam(required = false) LocalDate effectiveEndDate) {
    try {
      var revenueReport = revenueManagementService.report(effectiveStartDate, effectiveEndDate);
      return new ResponseEntity<>(revenueReport, HttpStatus.OK);
    } catch (FamilyTreeException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
