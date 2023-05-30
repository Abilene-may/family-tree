package com.example.familytree.controller;

import com.example.familytree.domain.ExpenseDetail;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.service.ExpenseDetailService;
import io.swagger.v3.oas.annotations.media.Schema;
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

/** Quản lý các giao dịch chi */
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/expense-detail")
public class ExpenseDetailController {
  private final ExpenseDetailService expenseDetailService;

  /**
   * API Thêm một khoản tài trợ trong chi tiết các giao dịch
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "Thêm một khoản tài trợ trong chi tiết các giao dịch")
  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody ExpenseDetail expenseDetail) {
    try {
      var detail = expenseDetailService.create(expenseDetail);
      return new ResponseEntity<>(detail, HttpStatus.OK);
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
   * API Sửa một khoản tài trợ trong chi tiết các giao dịch
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "Sửa một khoản tài trợ trong chi tiết các giao dịch")
  @PutMapping("/update")
  public ResponseEntity<Object> update(@RequestBody ExpenseDetail expenseDetail) {
    try {
      expenseDetailService.update(expenseDetail);
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
   * API Xóa một khoản tài trợ trong chi tiết các giao dịch
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "Xóa một khoản tài trợ trong chi tiết các giao dịch")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    try {
      expenseDetailService.delete(id);
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
   * API Xem danh sách khoản tài trợ trong chi tiết các giao dịch
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "Xem danh sách khoản tài trợ trong chi tiết các giao dịch")
  @GetMapping("/get-all")
  public ResponseEntity<Object> getAll(@RequestParam Long expenseManagementId) {
    try {
      var revenueDetailList = expenseDetailService.getAll(expenseManagementId);
      return new ResponseEntity<>(revenueDetailList, HttpStatus.OK);
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
   * API Xem 1 khoản tài trợ trong chi tiết các giao dịch
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "Xem 1 khoản tài trợ trong chi tiết các giao dịch")
  @GetMapping("/get-by-id/{id}")
  public ResponseEntity<Object> getById(@PathVariable Long id) {
    try {
      var revenueDetail = expenseDetailService.getById(id);
      return new ResponseEntity<>(revenueDetail, HttpStatus.OK);
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
