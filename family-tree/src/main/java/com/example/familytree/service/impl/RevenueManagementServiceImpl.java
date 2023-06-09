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
import com.example.familytree.service.RevenueManagementService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RevenueManagementServiceImpl implements RevenueManagementService {

  private final RevenueManagementRepository revenueManagementRepository;
  private final RevenueDetailRepository revenueDetailRepository;

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
      var startDate = today.plusDays(3);
      var dueDate = startDate.plusMonths(2);
      RevenueManagement management =
          RevenueManagement.builder()
              .year(startDate.getYear())
              .revenueName(Constant.QUY_HO)
              .revenuePerPerson(Constant.REVENUE_PER_PERSON)
              .startDate(startDate)
              .dueDate(dueDate)
              .status(Constant.SAP_DIEN_RA)
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
          ExceptionUtils.STATUS_IS_CLOSED,
          ExceptionUtils.messages.get(ExceptionUtils.STATUS_IS_CLOSED));
    }
    revenueManagement.setRevenuePerPerson(revenueManagementDIS.getRevenuePerPerson());
    // Kiểm tra hạn thu đã qua thì status = đã đóng
    LocalDate localDate = LocalDate.now();
    if (localDate.isAfter(revenueManagement.getDueDate())) {
      revenueManagement.setStatus(Constant.DA_DONG);
    }
    // ngày bđ thu <= today <= hạn thu thì status = đang diễn ra
    if (localDate.isAfter(revenueManagement.getStartDate())
        && localDate.isBefore(revenueManagement.getDueDate())) {
      revenueManagement.setStatus(Constant.DANG_DIEN_RA);
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
          ExceptionUtils.REVENUE_ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.REVENUE_ID_IS_NOT_EXIST));
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
    Long totalMoney = 0L;
    List<RevenueDetail> revenueDatels = new ArrayList<>();
    // check điều kiện đầu vào
    if(effectiveStartDate != null && effectiveEndDate != null){
      // tìm những khoản thu từ ngày đến ngày
      revenueDatels =
          revenueDetailRepository.findAllByStartDateAndEndDate(
              effectiveStartDate, effectiveEndDate);

    } else if(effectiveStartDate != null){
      // Tìm từ ngày
      revenueDatels =
          revenueDetailRepository.findAllByStartDate(effectiveStartDate);
    }
    else if (effectiveEndDate != null){
      // TH startDate == null
      revenueDatels =
          revenueDetailRepository.findAllByEndDate(effectiveEndDate);
    } else {
      // TH startDate == null và endDate == null
      revenueDatels =
          revenueDetailRepository.findAllByStatus();
    }
    revenueReport.setRevenueDetails(revenueDatels);
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
    // xóa 1 list các chi tiết khoản thu
    List<RevenueDetail> revenueDetailList = revenueDetailRepository.findAllByIdRevenueManagement(id);
    revenueDetailRepository.deleteAll(revenueDetailList);
  }

  public RevenueManagementServiceImpl(
      @Lazy RevenueManagementRepository revenueManagementRepository,
      @Lazy RevenueDetailRepository revenueDetailRepository) {
    super();
    this.revenueDetailRepository = revenueDetailRepository;
    this.revenueManagementRepository = revenueManagementRepository;
  }
}
