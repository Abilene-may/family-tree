package com.example.familytree.controller;

import com.example.familytree.domain.RevenueDetail;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.service.RevenueDetailService;
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
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/revenue-detail")
public class RevenueDetailController {
  private final RevenueDetailService revenueDetailService;

  /**
   * generate annual revenue
   *
   * @author nga
   * @since 25/05/2023
   */
  @Schema(name = "create an annual revenue detail")
  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody RevenueDetail RevenueDetail) {
    try {
      RevenueDetail management = revenueDetailService.create(RevenueDetail);
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
   * API get all annual revenues
   *
   * @author nga
   * @since 25/05/2023
   */
  @Schema(name = "get all annual revenues")
  @GetMapping("/get-all")
  public ResponseEntity<Object> getAll() {
    try {
      var RevenueDetails = revenueDetailService.getAll();
      return new ResponseEntity<>(RevenueDetails, HttpStatus.OK);
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
   * API get imformation revenue detail by id
   *
   * @author nga
   * @since 25/05/2023
   */
  @Schema(name = "get imformation revenue detail by id")
  @GetMapping("/get-by-id/{id}")
  public ResponseEntity<Object> getById(@PathVariable Long id) {
    try {
      var RevenueDetail = revenueDetailService.getById(id);
      return new ResponseEntity<>(RevenueDetail, HttpStatus.OK);
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
   * API update imformation revenue detail
   *
   * @author nga
   * @since 25/05/2023
   */
  @Schema(name = "update imformation revenue")
  @PutMapping("/update")
  public ResponseEntity<Object> update(@RequestBody RevenueDetail RevenueDetail) {
    try {
      revenueDetailService.update(RevenueDetail);
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
   * API delete imformation revenue detail by id
   *
   * @author nga
   * @since 25/05/2023
   */
  @Schema(name = "delete imformation revenue")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> update(@PathVariable Long id) {
    try {
      revenueDetailService.delete(id);
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
   * view the report of the revenue
   *
   * @author nga
   * @since 25/05/2023
   */
  @Schema(name = "the report of the revenue")
  @GetMapping("/report")
  public ResponseEntity<Object> report(
      @RequestParam Integer year, @RequestParam String typeOfRevenue) {
    try {
      var revenueReport = revenueDetailService.report(year, typeOfRevenue);
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
