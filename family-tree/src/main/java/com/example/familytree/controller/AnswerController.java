package com.example.familytree.controller;

import com.example.familytree.domain.EventManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.answer.AnswerReqDTO;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.service.AnswerService;
import com.example.familytree.service.EventManagementService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/answer")
public class AnswerController {
  private final AnswerService answerService;
  /**
   * API Tạo một phản hồi
   *
   * @author nga
   * @since 07/07/2023
   */
  @Schema(name = "Tạo một phản hồi")
  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody AnswerReqDTO answerReqDTO) {
    try {
      var answerDTO = answerService.create(answerReqDTO);
      return new ResponseEntity<>(answerDTO, HttpStatus.OK);
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
   * API Xem một phản hồi theo id của yêu cầu
   *
   * @author nga
   * @since 07/07/2023
   */
  @Schema(name = "Xem một phàn hồi theo id của yêu cầu")
  @GetMapping("/get-by-questionid/{id}")
  public ResponseEntity<Object> getByQuestionId(@PathVariable Long id) {
    try {
      var answer = answerService.getByQuestionId(id);
      return new ResponseEntity<>(answer, HttpStatus.OK);
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
