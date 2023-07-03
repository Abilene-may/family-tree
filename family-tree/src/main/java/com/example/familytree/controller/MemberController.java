package com.example.familytree.controller;

import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.UserDTO;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.models.membermanagement.SignUpReqDTO;
import com.example.familytree.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;

  /**
   * Đăng nhập
   *
   * @author nga
   * @since 02/05/2023
   */
  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Đăng nhập")
  public ResponseEntity<Object> logIn(@RequestBody UserDTO userDTO) {
    try {
      var member = memberService.logIn(userDTO.getUserName(), userDTO.getPassword());
      return new ResponseEntity<>(member, HttpStatus.OK);
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
   * Lấy danh sách tất cả thành viên trong gia phả
   *
   * @author nga
   * @since 02/05/2023
   */
  @GetMapping(value = "/get-all")
  @Operation(summary = "Lấy thông tin tất cả thành viên trong gia phả")
  public ResponseEntity<Object> getListMember(){
    var members =memberService.getAllMember();
    return new ResponseEntity<>(members,HttpStatus.OK);
  }

  /**
   * Tạo mới một thành viên đồng thời thêm mới user
   *
   * @author nga
   * @since 03/05/2023
   */
  @PostMapping(value = "create-member", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Tạo thành viên và user mới")
  public ResponseEntity<Object> createMember(@RequestBody Member member) {
    try {
      memberService.createMember(member);
      return ResponseEntity.ok().build();
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
   * Tìm thông tin thành viên theo id
   *
   * @author nga
   * @since 04/05/2023
   */
  @GetMapping(value = "/get-by-id-member",produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Tìm thông tin thành viên theo id")
  public ResponseEntity<Object> getMemberById(@RequestParam Long id){
    try {
      Member member = memberService.getMemberById(id);
      return new ResponseEntity<>(member, HttpStatus.OK);
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
   * sửa thông tin thành viên
   *
   * @author nga
   * @since 05/05/2023
   */
  @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "sửa thông tin thành viên")
  public ResponseEntity<Object> update(@RequestBody Member memberDTO)throws FamilyTreeException{
    try{
      memberService.update(memberDTO);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (FamilyTreeException e){
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
   * API tìm kiếm thông tin thành viên theo tên
   *
   * @author nga
   * @since 17/05/2023
   */
  @GetMapping (value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Tìm kiếm thông tin thành viên theo tên")
  public ResponseEntity<Object> search(@RequestParam String name) throws FamilyTreeException {
    try {
      var members = memberService.searchMemberByName(name);
      return new ResponseEntity<>(members, HttpStatus.OK);
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
   * API thống kê
   *
   * @author nga
   * @since 27/05/2023
   */
  @GetMapping (value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Thống kê trong gia phả")
  public ResponseEntity<Object> statistics() throws FamilyTreeException {
    try {
      var statisticsDTO = memberService.genealogicalStatisticsDTO();
      return new ResponseEntity<>(statisticsDTO, HttpStatus.OK);
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
   * API DS các tài khoản đã đăng ký
   *
   * @return
   * @since 01/07/2023
   * @author nga
   */
  @Operation(summary = "DS các thành viên đã đăng ký tài khoản login")
  @GetMapping(value = "/get-all-accounts", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getAllAccounts(){
    try {
      var allAccounts = memberService.findAllAccounts();
      return new ResponseEntity<>(allAccounts, HttpStatus.OK);
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
   * API Đăng ký tài khoản
   *
   * @return
   * @since 01/07/2023
   * @author nga
   */
  @Operation(summary = "Đăng ký tài khoản cho thành viên")
  @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> signUp(@RequestBody SignUpReqDTO reqDTO){
    try {
      memberService.signUp(reqDTO);
      return new ResponseEntity<>( HttpStatus.OK);
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
   * API Sửa thông tin tài khoản đã đăng ký
   *
   * @return
   * @since 03/07/2023
   * @author nga
   */
  @Operation(summary = "Sửa thông tin tài khoản đã đăng ký")
  @PostMapping(value = "/update-account", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> updateAccount(@RequestBody SignUpReqDTO reqDTO){
    try {
      memberService.updateAccount(reqDTO);
      return new ResponseEntity<>( HttpStatus.OK);
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
}
