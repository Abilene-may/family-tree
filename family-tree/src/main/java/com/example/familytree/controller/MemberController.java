package com.example.familytree.controller;

import com.example.familytree.FamilyTreeApplication;
import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.common.ErrorDTO;import com.example.familytree.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;import org.springframework.web.bind.annotation.RequestBody;import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;

  /**
   * Lấy danh sách tất cả thành viên trong gia phả
   *
   * @author nga
   * @since 02/05/2023
   */
  @GetMapping(value = "/get-all")
  @Operation(summary = "Lấy thông tin tất cả thành viên trong gia phả")
  public ResponseEntity<Object> getListMember(){
    List<Member> members =memberService.getAllMember();
    return new ResponseEntity<>(members,HttpStatus.OK);
  }

  /**
   * Tạo mới một thành viên đồng thời thêm mới user
   *
   * @author nga
   * @since 03/05/2023
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
}
