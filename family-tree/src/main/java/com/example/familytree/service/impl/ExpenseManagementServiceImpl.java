package com.example.familytree.service.impl;

import com.example.familytree.domain.ExpenseDetail;
import com.example.familytree.domain.ExpenseManagement;
import com.example.familytree.domain.RevenueDetail;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.ExpenseReport;
import com.example.familytree.models.ReAndExReport;
import com.example.familytree.repository.ExpenseDetailRepository;
import com.example.familytree.repository.ExpenseManagementRepository;
import com.example.familytree.service.ExpenseDetailService;
import com.example.familytree.service.ExpenseManagementService;
import com.example.familytree.service.FinancialSponsorshipService;
import com.example.familytree.service.RevenueManagementService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Service xử lý logic chi
 */
@Service
@Slf4j
public class ExpenseManagementServiceImpl implements ExpenseManagementService {
  private final ExpenseManagementRepository expenseManagementRepository;
  private final ExpenseDetailService expenseDetailService;
  private final RevenueManagementService revenueManagementService;
  private final FinancialSponsorshipService financialSponsorshipService;
  private final ExpenseDetailRepository expenseDetailRepository;

  public ExpenseManagementServiceImpl(
      @Lazy ExpenseManagementRepository expenseManagementRepository,
      @Lazy ExpenseDetailService expenseDetailService,
      @Lazy RevenueManagementService revenueManagementService,
      @Lazy FinancialSponsorshipService financialSponsorshipService,
      @Lazy ExpenseDetailRepository expenseDetailRepository) {
    super();
    this.expenseManagementRepository = expenseManagementRepository;
    this.expenseDetailService = expenseDetailService;
    this.revenueManagementService = revenueManagementService;
    this.financialSponsorshipService = financialSponsorshipService;
    this.expenseDetailRepository = expenseDetailRepository;
  }
  /**
   * Tạo một khoản chi theo năm
   *
   * @param expenseManagement
   * @author nga
   */
  @Override
  public ExpenseManagement create(ExpenseManagement expenseManagement) throws FamilyTreeException {
    var management = expenseManagementRepository.save(expenseManagement);
    return management;
  }
  /**
   * Sửa khoản chi theo năm
   *
   * @param expenseManagement
   * @author nga
   */
  @Override
  public void update(ExpenseManagement expenseManagement) throws FamilyTreeException {
    this.getById(expenseManagement.getId());
    expenseManagementRepository.save(expenseManagement);
  }
  /**
   * Xem danh sách các khoản chi
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public List<ExpenseManagement> getAll() throws FamilyTreeException {
    var expenseManagements = expenseManagementRepository.findAll();
    return expenseManagements;
  }
  /**
   * Xem khoản chi theo id
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public ExpenseManagement getById(Long id) throws FamilyTreeException {
    var expenseManagement = expenseManagementRepository.findById(id);
    if (expenseManagement.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.ID_IS_NOT_EXIST));
    }
    return expenseManagement.get();
  }
  /**
   * Báo cáo quản lý chi từ ngày đến ngày
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public ExpenseReport report(LocalDate effectiveStartDate, LocalDate effectiveEndDate)
      throws FamilyTreeException {
    ExpenseReport expenseReport = new ExpenseReport();
    // Lấy danh sách các khoản chi từ ngày đến ngày
    var expenseDetails  =
        expenseDetailRepository.findAllByStartDateAndEndDate(
            effectiveStartDate, effectiveEndDate);
    expenseReport.setExpenseDetails(expenseDetails);
    Long totalMoney = 0L;
    for (ExpenseDetail expenseDetail : expenseDetails) {
      // Tính tổng tiền từ danh sách giao dịch
      totalMoney += expenseDetail.getExpenseMoney();
    }
    expenseReport.setTotalExpense(totalMoney);
    return expenseReport;
  }
  /**
   * Báo cáo quản lý thu - chi từ ngày đến ngày
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public ReAndExReport revenueAndExpenseReport(
      LocalDate effectiveStartDate, LocalDate effectiveEndDate) throws FamilyTreeException {
    ReAndExReport reAndExReports = new ReAndExReport();
    // Lùi lại 1 ngày và tính số dư kỳ trước
    var previousBalance = this.getPreviousBalance(effectiveStartDate.minusDays(1));
    // tổng tiền thu
    var revenueReport = revenueManagementService.report(effectiveStartDate, effectiveEndDate);
    var totalRevenue = revenueReport.getTotalRevenue();
//    tổng tiền tài trợ
    var sponsorshipReport = financialSponsorshipService.report(effectiveStartDate, effectiveEndDate);
    var totalSposorship = sponsorshipReport.getTotalMoney();
    // tổng tiền chi
    var expenseReport = this.report(effectiveStartDate, effectiveEndDate);
    var totalExpense = expenseReport.getTotalExpense();
    // tính số dư còn lại
    Long remainingBalance = (previousBalance + totalRevenue + totalSposorship) - totalExpense;
    reAndExReports.setTotalRevenue(totalRevenue);
    reAndExReports.setTotalSposorship(totalSposorship);
    reAndExReports.setTotalExpense(totalExpense);
    reAndExReports.setRemainingBalance(remainingBalance);
    reAndExReports.setPreviousBalance(previousBalance);
    return reAndExReports;
  }
  /**
   * Xóa một quản lý chi
   *
   * @author nga
   * @since 06/06/2023
   */
  @Override
  public void delete(Long id) throws FamilyTreeException {
    var expenseManagement = this.getById(id);
    expenseManagementRepository.delete(expenseManagement);
    // xóa 1 list các chi tiết khoản chi
    List<ExpenseDetail> expenseDetails = expenseDetailRepository.findAllByExpenseManagementId(id);
    expenseDetailRepository.deleteAll(expenseDetails);
  }

  /**
   * Tính số tiền dư kỳ trước
   *
   * @param effectiveEndDate
   * @author ngant
   * @since 05/06/2023
   */
  public Long getPreviousBalance(LocalDate effectiveEndDate) throws FamilyTreeException{
    LocalDate effectiveStartDate = LocalDate.of(1800, 01, 01);
    // tổng tiền thu
    var revenueReport = revenueManagementService.report(effectiveStartDate, effectiveEndDate);
    var totalRevenue = revenueReport.getTotalRevenue();
//    tổng tiền tài trợ
    var sponsorshipReport = financialSponsorshipService.report(effectiveStartDate, effectiveEndDate);
    var totalSposorship = sponsorshipReport.getTotalMoney();
    // tổng tiền chi
    var expenseReport = this.report(effectiveStartDate, effectiveEndDate);
    var totalExpense = expenseReport.getTotalExpense();
    // tính số dư còn lại
    Long remainingBalance = (totalRevenue + totalSposorship) - totalExpense;
    return remainingBalance;
  }
}
