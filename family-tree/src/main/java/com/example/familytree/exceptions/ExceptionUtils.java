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
  public static final String ACCOUNT_DOES_NOT_HAVE = "ACCOUNT_DOES_NOT_HAVE";
  public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
  public static final String MOM_OR_DAD_IS_NOT_NULL = "MOM_OR_DAD_IS_NOT_NULL";
  public static final String TRUONG_HO_ALREADY_EXISTS = "TRUONG_HO_ALREADY_EXISTS";
  public static final String ONG_TO_ALREADY_EXISTS = "ONG_TO_ALREADY_EXISTS";
  public static final String WRONG_STATUS_REVENUE = "WRONG_STATUS_REVENUE";
  public static final String STATUS_IS_CLOSED = "STATUS_IS_CLOSED";
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
  public static final String R_ID_MEMBER_IS_NOT_EXIST = "R_ID_MEMBER_IS_NOT_EXIST";
  public static final String E_EVENT_IS_CLOSED = "E_EVENT_IS_CLOSED";
  public static final String MEMBER_HAD_A_WIFE_OR_HUSBAND = "MEMBER_HAD_A_WIFE_OR_HUSBAND";
  public static final String GUEST_ALREADY_EXISTS = "GUEST_ALREADY_EXISTS";
  public static final String NAME_PERMISSION_ALREADY_EXISTS = "NAME_PERMISSION_ALREADY_EXISTS";
  public static final String QUESTION_IS_NOT_EXITS = "QUESTION_IS_NOT_EXITS";
  public static final String Q_OTHER_STATUS_WAITING_FOR_APPROVAL = "Q_OTHER_STATUS_WAITING_FOR_APPROVAL";
  public static final String Q_HAS_BEEN_ANSWERED = "Q_HAS_BEEN_ANSWERED";
  public static final String ANSWER_DOES_NOT_EXITS = "ANSWER_DOES_NOT_EXITS";
  public static final String ROLES_DO_NOT_ALLOW_DELETE = "ROLES_DO_NOT_ALLOW_DELETE";

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
    messages.put(ExceptionUtils.USER_SIGNUP_1, "Thành viên đã đăng ký tài khoản vui lòng chọn thành viên khác khác.");
    messages.put(
        ExceptionUtils.ACCOUNT_DOES_NOT_HAVE,
        "Thành viên chưa đăng ký tài khoản. Vui lòng đăng ký tài khoản trước.");
    messages.put(ExceptionUtils.USER_ALREADY_EXISTS, "Username đã tồn tại. Vui lòng nhập username khác.");
    messages.put(ExceptionUtils.MOM_OR_DAD_IS_NOT_NULL,"Vui lòng nhập họ tên bố hoặc mẹ.");
    messages.put(ExceptionUtils.TRUONG_HO_ALREADY_EXISTS,"Trưởng họ đã tồn tại vui lòng chọn vai trò khác.");
    messages.put(ExceptionUtils.ONG_TO_ALREADY_EXISTS,"Ông tổ đã tồn tại vui lòng chọn vai trò khác.");
    messages.put(ExceptionUtils.WRONG_STATUS_REVENUE, "Đã quá hạn thu vui lòng chọn lại trạng thái.");
    messages.put(ExceptionUtils.STATUS_IS_CLOSED, "Trạng thái là đã đóng không thể sửa.");
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
        ExceptionUtils.R_ID_MEMBER_IS_NOT_EXIST, "Không tìm thấy thông tin của người đóng.");
    messages.put(
        ExceptionUtils.E_EVENT_IS_CLOSED, "Sự kiện đã đóng, không thể thiết lập khách mời.");
    messages.put(
        ExceptionUtils.MEMBER_HAD_A_WIFE_OR_HUSBAND, "Thành viên đã kết hôn và có vợ/chồng.");
    messages.put(
        ExceptionUtils.GUEST_ALREADY_EXISTS, "Thành viên này đã có trong danh sách khách mời.");
    messages.put(
        ExceptionUtils.NAME_PERMISSION_ALREADY_EXISTS, "Tên nhóm quyền đã tồn tại trong gia phả. Vui lòng chọn tên khác.");
    messages.put(
      ExceptionUtils.QUESTION_IS_NOT_EXITS, "Không tìm thấy yêu cầu hoặc yêu cầu không tồn tại.");
    messages.put(
    ExceptionUtils.Q_OTHER_STATUS_WAITING_FOR_APPROVAL, "Trạng thái khác chờ phê duyệt không thể xóa hoặc sửa.");
    messages.put(
    ExceptionUtils.Q_HAS_BEEN_ANSWERED, "Yêu cầu này đã được phản hồi.");
    messages.put(
    ExceptionUtils.ANSWER_DOES_NOT_EXITS, "Không tìm thấy thông tin của phản hồi.");
    messages.put(
        ExceptionUtils.ROLES_DO_NOT_ALLOW_DELETE,
        "Role ông tổ, trưởng họ và thành viên là những role mặc định không thể xóa.");
  }

  public static String buildMessage(String messKey, Object... arg) {
    return String.format(ExceptionUtils.messages.get(messKey), arg);
  }
}
