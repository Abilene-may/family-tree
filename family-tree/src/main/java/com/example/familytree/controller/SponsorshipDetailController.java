package com.example.familytree.controller;

import com.example.familytree.domain.SponsorsipDetail;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.service.SponsorshipDetailService;
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

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sponsorship-detail")
public class SponsorshipDetailController {
  private final SponsorshipDetailService sponsorshipDetailService;
  /**
   * API Thêm một khoản tài trợ trong chi tiết các giao dịch
   *
   * @author nga
   * @since 30/05/2023
   */
  @Schema(name = "Thêm một khoản tài trợ trong chi tiết các giao dịch")
  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody SponsorsipDetail sponsorsipDetail) {
    try {
      var detail = sponsorshipDetailService.create(sponsorsipDetail);
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
  public ResponseEntity<Object> update(@RequestBody SponsorsipDetail sponsorsipDetail) {
    try {
      sponsorshipDetailService.update(sponsorsipDetail);
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
      sponsorshipDetailService.delete(id);
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
  public ResponseEntity<Object> getAll(@RequestParam Long financialSponsorshipId) {
    try {
      var revenueDetailList = sponsorshipDetailService.getAll(financialSponsorshipId);
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
      var revenueDetail = sponsorshipDetailService.getById(id);
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
