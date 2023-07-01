package com.example.familytree.exceptions;

/**
 * Class khai báo exception
 *
 * @author nga
 */

import java.util.HashMap;import java.util.Map;
public class ExceptionUtils {
  public static final String E_INTERNAL_SERVER = "E_INTERNAL_SERVER";
  public static final String REVENUE_ID_IS_NOT_EXIST = "REVENUE_ID_IS_NOT_EXIST";
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
  public static final String GUEST_MANAGEMENT_IS_EMPTY = "GUEST_MANAGEMENT_IS_EMPTY";
  public static final String PERMISSION_MANAGEMENT_IS_NOT_BLANK = "PERMISSION_MANAGEMENT_IS_NOT_BLANK";
  public static final String MEMBER_ID_IS_NOT_EXIST = "MEMBER_ID_IS_NOT_EXIST";
  public static final String EXPENSE_ID_IS_NOT_EXIST = "EXPENSE_ID_IS_NOT_EXIST";
  public static final String SPONSORSHIP_ID_IS_NOT_EXIST = "SPONSORSHIP_ID_IS_NOT_EXIST";
  public static final String SPONSOR_ID_IS_NOT_EXIST = "SPONSOR_ID_IS_NOT_EXIST";
  public static final String EVENT_ID_IS_NOT_EXIST = "EVENT_ID_IS_NOT_EXIST";
  public static final String GUEST_ID_IS_NOT_EXIST = "GUEST_ID_IS_NOT_EXIST";
  public static final String PERMISSION_ID_IS_NOT_EXIST = "PERMISSION_ID_IS_NOT_EXIST";
  public static final String REVENUE_DETAIL_ID_IS_NOT_EXIST = "REVENUE_DETAIL_ID_IS_NOT_EXIST";



  public static Map<String, String> messages;

  static {
    messages = new HashMap<>();
    messages.put(ExceptionUtils.E_INTERNAL_SERVER, "Server không phản hồi.");
    messages.put(
        ExceptionUtils.REVENUE_ID_IS_NOT_EXIST,
        "Không tìm thấy thông tin khoản thu hằng năm hoặc thông tin không tồn tại.");
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
    messages.put(ExceptionUtils.GUEST_MANAGEMENT_IS_EMPTY, "Danh sách khách mời đang rỗng, vui lòng thiết lập khách mời.");
    messages.put(ExceptionUtils.PERMISSION_MANAGEMENT_IS_NOT_BLANK, "Tên nhóm quyền không được bỏ trống.");
    messages.put(
        ExceptionUtils.MEMBER_ID_IS_NOT_EXIST,
        "Không tìm thấy thông tin của thành viên hoặc thành viên không có trong gia phả.");
    messages.put(
        ExceptionUtils.EXPENSE_ID_IS_NOT_EXIST,
        "Không tìm thấy thông tin của quản lý chi hoặc quản lý chi không tồn tại.");
    messages.put(
        ExceptionUtils.SPONSORSHIP_ID_IS_NOT_EXIST,
        "Không tìm thấy thông tin của quản lý tiền tài trợ hoặc quản lý tiền tài trợ không tồn tại.");
    messages.put(
        ExceptionUtils.SPONSOR_ID_IS_NOT_EXIST, "Không tìm thấy thông tin của người tài trợ.");
    messages.put(
        ExceptionUtils.EVENT_ID_IS_NOT_EXIST,
        "Không tìm thấy thông tin của sự kiện hoặc sự kiện không tồn tại.");
    messages.put(ExceptionUtils.GUEST_ID_IS_NOT_EXIST, "Không tìm thấy thông tin của khách mời.");
    messages.put(
        ExceptionUtils.PERMISSION_ID_IS_NOT_EXIST, "Không tìm thấy thông tin của nhóm quyền.");
    messages.put(
        ExceptionUtils.REVENUE_DETAIL_ID_IS_NOT_EXIST, "Không tìm thấy thông tin của người thu.");
  }

  public static String buildMessage(String messKey, Object... arg) {
    return String.format(ExceptionUtils.messages.get(messKey), arg);
  }
}
