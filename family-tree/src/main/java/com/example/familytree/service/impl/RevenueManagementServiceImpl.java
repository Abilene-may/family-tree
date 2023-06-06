package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.RevenueDetail;
import com.example.familytree.domain.RevenueManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.RevenueManagementDIS;
import com.example.familytree.models.RevenueReport;
import com.example.familytree.repository.RevenueDetailRepository;
import com.example.familytree.repository.RevenueManagementRepository;
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
  private final RevenueDetailRepository revenueDetailRepository;
  private final RevenueDetailService revenueDetailService;

  /**
   * thiết lập một khoản thu hằng năm
   *
   * @author nga
   */
  @Override
  public List<RevenueManagement> create() throws FamilyTreeException {
    // Kiểm tra đã thiết lập khoản thu cho năm hiện tại chưa
    LocalDate today = LocalDate.now();
    List<RevenueManagement> revenueManagements =
        revenueManagementRepository.findAllByYear(today.getYear());
    if (revenueManagements.isEmpty()) {
      today.plusDays(3);
      var dueDate = today.plusMonths(2);
      RevenueManagement management =
          RevenueManagement.builder()
              .year(today.getYear())
              .revenueName(Constant.QUY_HO)
              .revenuePerPerson(Constant.REVENUE_PER_PERSON)
              .startDate(today)
              .dueDate(dueDate)
              .status(Constant.SAP_MO)
              .build();
      revenueManagements.add(management);
      return revenueManagementRepository.saveAll(revenueManagements);
    } else {
      throw new FamilyTreeException(
          ExceptionUtils.REVENUE_ALREADY_EXISTS,
          ExceptionUtils.messages.get(ExceptionUtils.REVENUE_ALREADY_EXISTS));
    }
  }

  /**
   * Cập nhật lại khoản thu hằng năm
   *
   * @author nga
   */
  @Override
  public void update(RevenueManagementDIS revenueManagementDIS) throws FamilyTreeException {
    // kiểm tra đầu vào có tồn tại id không
    var revenueManagement = this.getById(revenueManagementDIS.getId());
    // kiểm tra trạng thái nếu status = đã đóng thì không được sửa
    if (revenueManagement.getStatus().equals(Constant.DA_DONG)) {
      throw new FamilyTreeException(
          ExceptionUtils.CLOSED_REVENUE,
          ExceptionUtils.messages.get(ExceptionUtils.CLOSED_REVENUE));
    }
    revenueManagement.setRevenuePerPerson(revenueManagementDIS.getRevenuePerPerson());
    // Kiểm tra hạn thu đã qua thì status = đã đóng
    LocalDate localDate = LocalDate.now();
    if (localDate.isAfter(revenueManagement.getDueDate())) {
      revenueManagement.setStatus(Constant.DA_DONG);
    }
    // ngày bđ thu <= today <= hạn thu thì status = đang mở
    if (localDate.isAfter(revenueManagement.getStartDate())
        && localDate.isBefore(revenueManagement.getDueDate())) {
      revenueManagement.setStatus(Constant.DANG_MO);
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
   * Báo cáo thu từ ngày đến ngày
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public RevenueReport report(LocalDate effectiveStartDate, LocalDate effectiveEndDate)
      throws FamilyTreeException {
    RevenueReport revenueReport = new RevenueReport();
    // tìm những khoản thu từ ngày đến ngày
    var revenueDatels =
        revenueDetailRepository.findAllByStartDateAndEndDate(
            effectiveStartDate, effectiveEndDate);
    revenueReport.setRevenueDetails(revenueDatels);
    Long totalMoney = 0L;
    for (RevenueDetail revenueDetail: revenueDatels) {
      totalMoney +=  revenueDetail.getMoney();
    }
    revenueReport.setTotalRevenue(totalMoney);
    return revenueReport;
  }

  /**
   * Xóa một quản lý thu
   *
   * @author nga
   * @since 06/06/2023
   */
  @Override
  public void delete(Long id) throws FamilyTreeException {
    var revenueManagement = this.getById(id);
    revenueManagementRepository.delete(revenueManagement);
  }

  public RevenueManagementServiceImpl(
      @Lazy RevenueManagementRepository revenueManagementRepository,
      @Lazy RevenueDetailService revenueDetailService,
      @Lazy RevenueDetailRepository revenueDetailRepository) {
    super();
    this.revenueDetailRepository = revenueDetailRepository;
    this.revenueManagementRepository = revenueManagementRepository;
    this.revenueDetailService = revenueDetailService;
  }
}
