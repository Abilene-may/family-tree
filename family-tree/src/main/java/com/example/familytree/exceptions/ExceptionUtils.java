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
  public static final String MOM_OR_DAD_IS_NOT_NULL = "MOM_OR_DAD_IS_NOT_NULL";
  public static final String TRUONG_HO_ALREADY_EXISTS = "TRUONG_HO_ALREADY_EXISTS";
  public static final String ONG_TO_ALREADY_EXISTS = "ONG_TO_ALREADY_EXISTS";
  public static final String WRONG_STATUS_REVENUE = "WRONG_STATUS_REVENUE";
  public static final String STATUS_IS_INCORRECT = "STATUS_IS_INCORRECT";
  public static final String CLOSED_EXPENSE = "CLOSED_EXPENSE";
  public static final String E_FINANCIAL_SPONSORSHIP_1 = "E_FINANCIAL_SPONSORSHIP_1";
  public static final String E_FINANCIAL_SPONSORSHIP_2 = "E_FINANCIAL_SPONSORSHIP_2";
  public static final String E_SPONSORSHIP_DETAIL_1 = "E_SPONSORSHIP_DETAIL_1";
  public static final String REVENUE_ALREADY_EXISTS = "REVENUE_ALREADY_EXISTS";
  public static final String EVENT_DATE_IS_NOT_NULL = "EVENT_STATUS_IS_INCORRECT_1";


  public static Map<String, String> messages;

  static {
    messages = new HashMap<>();
    messages.put(ExceptionUtils.E_INTERNAL_SERVER, "Server không phản hồi.");
    messages.put(ExceptionUtils.ID_IS_NOT_EXIST, "Không tìm thấy thông tin hoặc thông tin không tồn tại.");
    messages.put(ExceptionUtils.USER_SUCESSFUL, "Đăng nhập thành công.");
    messages.put(ExceptionUtils.USER_LOGIN_1, "Bạn chưa nhập thông tin username/password.");
    messages.put(ExceptionUtils.USER_LOGIN_2, "Username không đúng hoặc không tồn tại.");
    messages.put(ExceptionUtils.USER_LOGIN_3, "Sai mật khẩu vui lòng nhập lại.");
    messages.put(ExceptionUtils.USER_SIGNUP_1, "username đã tồn tại vui lòng nhập username khác.");
    messages.put(ExceptionUtils.MOM_OR_DAD_IS_NOT_NULL,"Vui lòng nhập họ tên bố hoặc mẹ.");
    messages.put(ExceptionUtils.TRUONG_HO_ALREADY_EXISTS,"Trưởng họ đã tồn tại vui lòng chọn vai trò khác.");
    messages.put(ExceptionUtils.ONG_TO_ALREADY_EXISTS,"Ông tổ đã tồn tại vui lòng chọn vai trò khác.");
    messages.put(ExceptionUtils.WRONG_STATUS_REVENUE, "Đã quá hạn thu vui lòng chọn lại trạng thái.");
    messages.put(ExceptionUtils.STATUS_IS_INCORRECT, "Trạng thái là đã đóng không thể sửa.");
    messages.put(ExceptionUtils.CLOSED_EXPENSE, "Năm không phải năm hiện tại vui lòng chọn lại trạng thái.");
    messages.put(ExceptionUtils.E_FINANCIAL_SPONSORSHIP_1, "Ngày mở không được sau ngày đóng.");
    messages.put(ExceptionUtils.E_FINANCIAL_SPONSORSHIP_2, "Đã quá hạn mở vui lòng chọn lại trạng thái.");
    messages.put(ExceptionUtils.E_SPONSORSHIP_DETAIL_1, "Đợt tài trợ này đã đóng, không thể thêm giao dịch.");
    messages.put(ExceptionUtils.REVENUE_ALREADY_EXISTS, "Khoản thu hằng năm của năm nay đã được thiết lập.");
    messages.put(ExceptionUtils.EVENT_DATE_IS_NOT_NULL, "Ngày tổ chức sự kiện không được để trống.");

  }
  public static String buildMessage(String messKey, Object... arg) {
    return String.format(ExceptionUtils.messages.get(messKey), arg);
  }
}
