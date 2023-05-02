package com.example.familytree.controller;

import com.example.familytree.domain.Member;
import com.example.familytree.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<List<Member>> getListMember(){
    List<Member> members =memberService.getAllMember();
    return new ResponseEntity<>(members,HttpStatus.OK);
  }
}
