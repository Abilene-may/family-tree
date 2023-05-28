package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.RevenueManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.repository.RevenueManagementRepository;
import com.example.familytree.service.MemberService;
import com.example.familytree.service.RevenueManagementService;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
public class RevenueManagementServiceImpl implements RevenueManagementService {

  private final RevenueManagementRepository revenueManagementRepository;
  private final MemberService memberService;

  /**
   * Tạo một khoản thu hằng năm
   *
   * @param revenueManagement
   * @author nga
   */
  @Override
  public RevenueManagement create(RevenueManagement revenueManagement) throws FamilyTreeException {
    // convert trạng thái về tiếng việt không dấu
    var status = memberService.deAccent(revenueManagement.getStatus());
    // kiểm tra xem hạn thu đã qua mà status vẫn là đang mở thì bảo lỗi
    LocalDate localDate = LocalDate.now();
    if (localDate.isAfter(revenueManagement.getDueDate()) && status.equals(Constant.DANG_MO)) {
      throw new FamilyTreeException(
          ExceptionUtils.WRONG_STATUS_REVENUE,
          ExceptionUtils.messages.get(ExceptionUtils.WRONG_STATUS_REVENUE));
    }
    revenueManagement.setStatusSearch(status);
    return revenueManagementRepository.save(revenueManagement);
  }

  /**
   * Cập nhật lại khoản thu hằng năm
   *
   * @param revenueManagement
   * @author nga
   */
  @Override
  public void update(RevenueManagement revenueManagement) throws FamilyTreeException {
    // kiểm tra đầu vào có tồn tại id không
    var management = this.getById(revenueManagement.getId());
    // set trạng thái
    var status = memberService.deAccent(revenueManagement.getStatus());
    // kiểm tra trạng thái nếu status = đã đóng thì không được sửa
    if (status.equals(Constant.DA_DONG)) {
      throw new FamilyTreeException(
          ExceptionUtils.CLOSED_REVENUE,
          ExceptionUtils.messages.get(ExceptionUtils.CLOSED_REVENUE));
    }
    // Kiêểm tra hạn thu đã qua thì status = đã đóng
    LocalDate localDate = LocalDate.now();
    if (localDate.isAfter(revenueManagement.getDueDate()) && status.equals(Constant.DANG_MO)) {
      throw new FamilyTreeException(
          ExceptionUtils.WRONG_STATUS_REVENUE,
          ExceptionUtils.messages.get(ExceptionUtils.WRONG_STATUS_REVENUE));
    }
    revenueManagement.setStatusSearch(status);
    revenueManagementRepository.save(revenueManagement);
  }

  @Override
  public List<RevenueManagement> getAllRevenue() throws FamilyTreeException {
    var revenueManagements = revenueManagementRepository.findAll();
    return revenueManagements;
  }

  /**
   * Tìm kiếm theo id
   *
   * @param id
   * @author nga
   */
  @Override
  public RevenueManagement getById(Long id) throws FamilyTreeException {
    var revenueManagement = revenueManagementRepository.findById(id);
    if (revenueManagement.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.ID_IS_NOT_EXIST));
    }
    return revenueManagement.get();
  }
}
