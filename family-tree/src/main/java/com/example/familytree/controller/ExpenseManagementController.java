package com.example.familytree.controller;

import com.example.familytree.domain.ExpenseManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.service.ExpenseManagementService;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/expense-management")
public class ExpenseManagementController {
  private final ExpenseManagementService expenseManagementService;
  /**
   * Thêm mới một khoản chi
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "create an annual revenue")
  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody ExpenseManagement expenseManagement) {
    try {
      ExpenseManagement management = expenseManagementService.create(expenseManagement);
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
   * API Xem DS khoản chi
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "get all annual revenues")
  @GetMapping("/get-all")
  public ResponseEntity<Object> getAll() {
    try {
      var expenseManagements = expenseManagementService.getAll();
      return new ResponseEntity<>(expenseManagements, HttpStatus.OK);
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
   * @since 30/05/2023
   */
  @Schema(name = "get imformation revenue by id")
  @GetMapping("/get-by-id/{id}")
  public ResponseEntity<Object> getById(@PathVariable Long id) {
    try {
      var revenueManagement = expenseManagementService.getById(id);
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
   * API cập nhật thông tin khoản chi
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "update imformation revenue")
  @PutMapping("/update")
  public ResponseEntity<Object> update(@RequestBody ExpenseManagement expenseManagement) {
    try {
      expenseManagementService.update(expenseManagement);
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
   * API Xóa một quản lý chi
   *
   * @author nga
   * @since 06/06/2023
   */
  @Schema(name = "Xóa một quản lý chi")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    try {
      expenseManagementService.delete(id);
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
   * API Báo cáo quản lý chi từ ngày đến ngày
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "Báo cáo khoản thu từ ngày đến ngày")
  @GetMapping("/report")
  public ResponseEntity<Object> report(@RequestParam LocalDate effectiveStartDate,
      @RequestParam LocalDate effectiveEndDate) {
    try {
      var revenueReport = expenseManagementService.report(effectiveStartDate, effectiveEndDate);
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

  /**
   * API Báo cáo Thu - chi từ ngày đến ngày
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "Báo cáo thu-chi từ ngày đến ngày")
  @GetMapping("/financial-statement")
  public ResponseEntity<Object> financialStatement(@RequestParam LocalDate effectiveStartDate,
      @RequestParam LocalDate effectiveEndDate) {
    try {
      var reAndExReport =
          expenseManagementService.revenueAndExpenseReport(effectiveStartDate, effectiveEndDate);
      return new ResponseEntity<>(reAndExReport, HttpStatus.OK);
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
