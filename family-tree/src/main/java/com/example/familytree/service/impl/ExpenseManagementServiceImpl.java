package com.example.familytree.service.impl;

import com.example.familytree.domain.ExpenseManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.ExpenseReport;
import com.example.familytree.models.ReAndExReport;
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

  public ExpenseManagementServiceImpl(
      @Lazy ExpenseManagementRepository expenseManagementRepository,
      @Lazy ExpenseDetailService expenseDetailService,
      @Lazy RevenueManagementService revenueManagementService,
      @Lazy FinancialSponsorshipService financialSponsorshipService) {
    super();
    this.expenseManagementRepository = expenseManagementRepository;
    this.expenseDetailService = expenseDetailService;
    this.revenueManagementService = revenueManagementService;
    this.financialSponsorshipService = financialSponsorshipService;
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
   * Báo cáo quản lý chi
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public ExpenseReport report(Integer year) throws FamilyTreeException {
    ExpenseReport expenseReport = new ExpenseReport();
    var revenueManagements = expenseManagementRepository.findAllByYear(year);
    expenseReport.setExpenseManagements(revenueManagements);
    Long totalMoney = 0L;
    for (ExpenseManagement expenseManagement : revenueManagements) {
      // Lấy tổng tiền từ danh sách giao dịch
      var expenseDetailDTO = expenseDetailService.getAll(expenseManagement.getId());
      totalMoney += (expenseDetailDTO.getTotalMoney());
    }
    expenseReport.setTotalExpense(totalMoney);
    return expenseReport;
  }
  /**
   * Báo cáo quản lý chi
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public List<ReAndExReport> revenueAndExpenseReport() throws FamilyTreeException {
    List<ReAndExReport> reAndExReports = new ArrayList<>();
    // Báo cáo thu chi trong 10 năm đổ về
    LocalDate today = LocalDate.now();
    Integer year = today.getYear();
    for(int i=0; i < 9; i++){
      var revenueReport = revenueManagementService.report(year-i);
      var expenseReport = this.report(year-i);
      var sponsorshipReport = financialSponsorshipService.report(year-i);
      Long totalRevenue = revenueReport.getTotalRevenue();
      Long totalExpense = expenseReport.getTotalExpense();
      Long totalSponsorship = sponsorshipReport.getTotalMoney();
      if(totalRevenue != 0L || totalExpense != 0L){
        ReAndExReport reAndExReport = new ReAndExReport();
        // thiết lập tiền thu chi trong năm
        Long total = totalRevenue + totalSponsorship;
        reAndExReport.setYear(year-i);
        reAndExReport.setTotalRevenue(total);
        reAndExReport.setTotalExpense(totalExpense);
        // Tính số tiền còn lại
        reAndExReport.setRemainingBalance(total - totalExpense);
        reAndExReports.add(reAndExReport);
      }
    }
    return reAndExReports;
  }

}
