package com.example.familytree.service.impl;

import com.example.familytree.domain.User;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.repository.UserRepository;
import com.example.familytree.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  /**
   * đăng nhập vào tài khoản
   *
   * @param userName, password
   * @author nga
   */
  @Override
  public void logIn(String userName, String password) throws FamilyTreeException {
    if (userName == null || password == null) {
      throw new FamilyTreeException(
          ExceptionUtils.USER_LOGIN_1, ExceptionUtils.messages.get(ExceptionUtils.USER_LOGIN_1));
    } else {
      Optional<User> userCheck = userRepository.findByUserName(userName);
      if (userCheck.isEmpty()) {
        throw new FamilyTreeException(
            ExceptionUtils.USER_LOGIN_2, ExceptionUtils.messages.get(ExceptionUtils.USER_LOGIN_2));
        }
        if (!password.equals(userCheck.get().getPassword())) {
          throw new FamilyTreeException(
              ExceptionUtils.USER_LOGIN_3, ExceptionUtils.messages.get(ExceptionUtils.USER_LOGIN_3));
        }
      }
  }
  /**
   * đăng ký tài khoản
   *
   * @param userName, password
   * @author nga
   */
  @Override
  public void signUp(String userName, String password, String role) throws FamilyTreeException {
    Optional<User> userCheck = userRepository.findByUserName(userName);
    if(userCheck.isPresent()){
      throw new FamilyTreeException(
          ExceptionUtils.USER_SIGNUP_1, ExceptionUtils.messages.get(ExceptionUtils.USER_SIGNUP_1));
    }
    userCheck.get().setRole(role);
    userRepository.save(userCheck.get());
  }
}
