package com.example.familytree.exceptions;

import java.util.HashMap;import java.util.Map;
public class ExceptionUtils {
  public static final String E_INTERNAL_SERVER = "E_INTERNAL_SERVER";
  public static final String ID_IS_NOT_EXIST = "ID_IS_NOT_EXIST";
  public static final String USER_SUCESSFUL = "USER_SUCESSFUL";
  public static final String USER_LOGIN_1 = "USER_LOGIN_1";
  public static final String USER_LOGIN_2 = "USER_LOGIN_2";
  public static final String USER_LOGIN_3 = "USER_LOGIN_3";
  public static final String USER_SIGNUP_1 = "USER_SIGNUP_1";

  public static Map<String, String> messages;

  static {
    messages = new HashMap<>();
    messages.put(ExceptionUtils.E_INTERNAL_SERVER, "Server không phản hồi");
    messages.put(ExceptionUtils.ID_IS_NOT_EXIST, "Không tìm thấy id hoặc id không tồn tại");
    messages.put(ExceptionUtils.USER_SUCESSFUL, "Đăng nhập thành công");
    messages.put(ExceptionUtils.USER_LOGIN_1, "Bạn chưa nhập thông tin username/password");
    messages.put(ExceptionUtils.USER_LOGIN_2, "Username không đúng hoặc không tồn tại");
    messages.put(ExceptionUtils.USER_LOGIN_3, "Sai mật khẩu vui lòng nhập lại");
    messages.put(ExceptionUtils.USER_SIGNUP_1, "username đã tồn tại vui lòng nhập username khác");
  }
  public static String buildMessage(String messKey, Object... arg) {
    return String.format(ExceptionUtils.messages.get(messKey), arg);
  }
}