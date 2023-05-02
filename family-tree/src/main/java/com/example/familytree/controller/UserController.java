package com.example.familytree.controller;

import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.UserDTO;
import com.example.familytree.models.common.ErrorDTO;
import com.example.familytree.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

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
      userService.logIn(userDTO.getUserName(), userDTO.getPassword());
    } catch (FamilyTreeException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Đăng ký
   *
   * @author nga
   * @since 02/0352023
   */
  @PostMapping(value = "signup", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Đăng ký")
  public ResponseEntity<Object> signUp(@RequestBody UserDTO userDTO) {
    try {
      userService.signUp(userDTO.getUserName(), userDTO.getPassword(), userDTO.getRole());
    } catch (FamilyTreeException e) {
      return new ResponseEntity<>(
          new ErrorDTO(e.getMessageKey(), e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return new ResponseEntity<>(
          ExceptionUtils.messages.get(ExceptionUtils.E_INTERNAL_SERVER),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
