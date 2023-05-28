package com.example.familytree.controller;

import com.example.familytree.domain.RevenueDetail;
import com.example.familytree.domain.RevenueManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.RevenueDetailDTO;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.service.RevenueDetailService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
   * API Lấy list thành viên cần đóng khoản thu hàng năm
   *
   * @author nga
   * @since 28/05/2023
   */
  @Schema(name = "Lấy list thành viên cần đóng khoản thu hàng năm")
  @GetMapping("/get-all")
  public ResponseEntity<Object> getAll(@RequestParam Long idRevenueManagement) {
    try {
      var revenueDetailList = revenueDetailService.getAllByIdRevenueManagement(idRevenueManagement);
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
   * API cập nhật thông tin khoản thu của từng thành viên
   *
   * @author nga
   * @since 28/05/2023
   */
  @Schema(name = "update imformation revenue")
  @PutMapping("/update")
  public ResponseEntity<Object> update(@RequestBody RevenueDetailDTO revenueDetailDTO) {
    try {
      revenueDetailService.update(revenueDetailDTO);
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
}
