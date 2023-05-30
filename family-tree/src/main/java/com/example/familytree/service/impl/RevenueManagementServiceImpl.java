package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.RevenueManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.RevenueReport;
import com.example.familytree.repository.RevenueManagementRepository;
import com.example.familytree.service.MemberService;
import com.example.familytree.service.RevenueDetailService;
import com.example.familytree.service.RevenueManagementService;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RevenueManagementServiceImpl implements RevenueManagementService {

  private final RevenueManagementRepository revenueManagementRepository;
  private final MemberService memberService;
  private final RevenueDetailService revenueDetailService;

  /**
   * Tạo một khoản thu hằng năm
   *
   * @param revenueManagement
   * @author nga
   */
  @Override
  public RevenueManagement create(RevenueManagement revenueManagement) throws FamilyTreeException {
    // kiểm tra xem hạn thu đã qua mà status vẫn là đang mở thì bảo lỗi
    LocalDate localDate = LocalDate.now();
    if (localDate.isAfter(revenueManagement.getDueDate())
        && revenueManagement.getStatus().equals(Constant.DANG_MO)) {
      throw new FamilyTreeException(
          ExceptionUtils.WRONG_STATUS_REVENUE,
          ExceptionUtils.messages.get(ExceptionUtils.WRONG_STATUS_REVENUE));
    }
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
    // kiểm tra trạng thái nếu status = đã đóng thì không được sửa
    if (revenueManagement.getStatus().equals(Constant.DA_DONG)) {
      throw new FamilyTreeException(
          ExceptionUtils.CLOSED_REVENUE,
          ExceptionUtils.messages.get(ExceptionUtils.CLOSED_REVENUE));
    }
    // Kiểm tra hạn thu đã qua thì status = đã đóng
    LocalDate localDate = LocalDate.now();
    if (localDate.isAfter(revenueManagement.getDueDate())
        && revenueManagement.getStatus().equals(Constant.DANG_MO)) {
      throw new FamilyTreeException(
          ExceptionUtils.WRONG_STATUS_REVENUE,
          ExceptionUtils.messages.get(ExceptionUtils.WRONG_STATUS_REVENUE));
    }
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

  /**
   * Báo cáo thu theo năm
   * @param year
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public RevenueReport report(Integer year) throws FamilyTreeException {
    RevenueReport revenueReport = new RevenueReport();
    var revenueManagements = revenueManagementRepository.findAllByYear(year);
    revenueReport.setRevenueManagements(revenueManagements);
    Long totalMoney = 0L;
    for (RevenueManagement revenueManagement: revenueManagements) {
      // Lấy tổng tiền từ danh sách giao dịch
      var revenueDetailList  = revenueDetailService.getAllByIdRevenueManagement(revenueManagement.getId());
      totalMoney += (revenueDetailList.size() * revenueManagement.getRevenuePerPerson());
    }
    revenueReport.setTotalRevenue(totalMoney);
    return revenueReport;
  }

  public RevenueManagementServiceImpl(
      @Lazy RevenueManagementRepository revenueManagementRepository,
      @Lazy RevenueDetailService revenueDetailService,
      @Lazy MemberService memberService) {
    super();
    this.memberService = memberService;
    this.revenueManagementRepository = revenueManagementRepository;
    this.revenueDetailService = revenueDetailService;
  }
}
