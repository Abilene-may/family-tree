package com.example.familytree.controller;

import com.example.familytree.domain.Question;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.models.guestmanagement.GuestManagementReqDTO;
import com.example.familytree.models.question.QuestionReqDTO;
import com.example.familytree.service.QuestionService;
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
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/question")
public class QuestionController {
  private final QuestionService questionService;

  /**
   * API Tạo một yêu cầu gửi đến quản lý
   *
   * @author nga
   * @since 07/07/2023
   */
  @Schema(name = "Tạo một yêu cầu gửi đến quản lý")
  @PostMapping("/create")
  public ResponseEntity<Object> create(
      @RequestBody QuestionReqDTO reqDTO) {
    try {
      var q = questionService.create(reqDTO);
      return new ResponseEntity<>(q, HttpStatus.OK);
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
   * API Xem thông tin một yêu cầu theo id
   *
   * @author nga
   * @since 07/07/2023
   */
  @Schema(name = "Xem thông tin một yêu cầu theo id")
  @GetMapping("/get-by-id/{id}")
  public ResponseEntity<Object> getById(@PathVariable Long id) {
    try {
      var question = questionService.getById(id);
      return new ResponseEntity<>(question, HttpStatus.OK);
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
   * API Xóa một yêu cầu theo id
   *
   * @author nga
   * @since 07/07/2023
   */
  @Schema(name = "Xóa một yêu cầu theo id")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    try {
      questionService.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
