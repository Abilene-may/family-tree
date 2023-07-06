package com.example.familytree.controller;

import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.models.guestmanagement.GuestManagementReqDTO;
import com.example.familytree.models.guestmanagement.GuestReqCreate;
import com.example.familytree.service.GuestManagementService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.familytree.domain.GuestManagement;


@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/guest-management")
public class GuestManagementController {
  private final GuestManagementService guestManagementService;
  /**
   * API Thiết lập khách mời theo điều kiện tìm kiếm
   *
   * @author nga
   * @since 16/06/2023
   */
  @Schema(name = "Thiết lập khách mời theo điều kiện tìm kiếm")
  @PostMapping("/set-up-guest")
  public ResponseEntity<Object> create(
      @RequestBody(required = false) GuestManagementReqDTO guestManagementReqDTO) {
    try {
      var management = guestManagementService.setUpListGuest(guestManagementReqDTO);
      return new ResponseEntity<>(management, HttpStatus.OK);
    } catch (FamilyTreeException e) {
      log.error(e.getMessage(), e);
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
   * API Xem danh sách tất cả các khách mời theo id sự kiện
   *
   * @author nga
   * @since 16/06/2023
   */
  @Schema(name = "Xem danh sách các khách mời theo id sự kiện")
  @GetMapping("/get-all-an-event")
  public ResponseEntity<Object> getAllByEventManagementId(@RequestParam Long eventManagementId) {
    try {
      var guestManagements = guestManagementService.findAllByEventManagementId(eventManagementId);
      return new ResponseEntity<>(guestManagements, HttpStatus.OK);
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
   * API Xóa một khách mời
   * id của khách mời
   * @author nga
   * @since 16/06/2023
   */
  @Schema(name = "Xóa một khách mời")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    try {
      guestManagementService.delete(id);
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
   * API Thêm mới một khách mời
   * id của khách mời
   * @author nga
   * @since 05/07/2023
   */
  @Schema(name = "Thêm mới một khách mời")
  @PostMapping("/create")
  public ResponseEntity<Object> createGuest(@RequestBody GuestReqCreate req) {
    try {
      var guest = guestManagementService.createGuest(req);
      return new ResponseEntity<>(guest, HttpStatus.OK);
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
   * API Xóa tất cả khách mời của một sự kiện
   *
   * @author nga
   * @since 16/06/2023
   */
  @Schema(name = "Xóa khách mời tất cả khách mời của một sự kiện")
  @DeleteMapping("/delete-all/{eventManagementId}")
  public ResponseEntity<Object> deleteAll(@PathVariable Long eventManagementId) {
    try {
      guestManagementService.deleteAll(eventManagementId);
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
}
