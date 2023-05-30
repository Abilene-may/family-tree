package com.example.familytree.controller;

import com.example.familytree.domain.FinancialSponsorship;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.service.FinancialSponsorshipService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/financial-sponorship")
public class FinancialSponorshipController {
  private final FinancialSponsorshipService financialSponsorshipService;
  /**
   * Thêm mới một khoản tài trợ
   *
   * @author nga
   * @since 29/05/2023
   */
  @Schema(name = "Thêm mới một khoản tài trợ")
  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody FinancialSponsorship financialSponsorship) {
    try {
      var management = financialSponsorshipService.create(financialSponsorship);
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
   * API xem danh sách các khoản tài trợ
   *
   * @author nga
   * @since 29/05/2023
   */
  @Schema(name = " xem danh sách các khoản tài trợ")
  @GetMapping("/get-all")
  public ResponseEntity<Object> getAll() {
    try {
      var revenueManagements = financialSponsorshipService.getAll();
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
   * API tìm thông tin theo id tài trợ
   *
   * @author nga
   * @since 29/05/2023
   */
  @Schema(name = "tìm thông tin theo id tài trợ")
  @GetMapping("/get-by-id/{id}")
  public ResponseEntity<Object> getById(@PathVariable Long id) {
    try {
      var revenueManagement = financialSponsorshipService.getById(id);
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
   * API cập nhật thông tin khoản tài trợ theo năm
   *
   * @author nga
   * @since 29/05/2023
   */
  @Schema(name = "cập nhật thông tin khoản tài trợ theo năm")
  @PutMapping("/update")
  public ResponseEntity<Object> update(@RequestBody FinancialSponsorship financialSponsorship) {
    try {
      financialSponsorshipService.update(financialSponsorship);
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
   * API Báo cáo khoản tài trợ theo năm
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "Báo cáo khoản tài trợ theo năm")
  @GetMapping("/report")
  public ResponseEntity<Object> report(@RequestParam Integer year) {
    try {
      var financialSponsorshipReport = financialSponsorshipService.report(year);
      return new ResponseEntity<>(financialSponsorshipReport, HttpStatus.OK);
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
