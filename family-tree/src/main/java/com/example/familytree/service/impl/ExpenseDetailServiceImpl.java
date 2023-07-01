package com.example.familytree.service.impl;

import com.example.familytree.domain.ExpenseDetail;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.ExpenseDetailDTO;
import com.example.familytree.repository.ExpenseDetailRepository;
import com.example.familytree.service.ExpenseDetailService;
import com.example.familytree.service.ExpenseManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExpenseDetailServiceImpl implements ExpenseDetailService {

  private final ExpenseDetailRepository expenseDetailRepository;
  private final ExpenseManagementService expenseManagementService;

  public ExpenseDetailServiceImpl(
      @Lazy ExpenseDetailRepository expenseDetailRepository,
      @Lazy ExpenseManagementService expenseManagementService) {
    super();
    this.expenseDetailRepository = expenseDetailRepository;
    this.expenseManagementService = expenseManagementService;
  }
  /**
   * Tạo một giao dịch chi theo năm
   *
   * @param expenseDetail
   * @author nga
   */
  @Override
  public ExpenseDetail create(ExpenseDetail expenseDetail) throws FamilyTreeException {
    var expenseManagement =
        expenseManagementService.getById(expenseDetail.getExpenseManagementId());
    expenseDetail.setYear(expenseManagement.getYear());
    var detail = expenseDetailRepository.save(expenseDetail);
    return detail;
  }
  /**
   * Sửa một giao dịch chi
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public void update(ExpenseDetail expenseDetail) throws FamilyTreeException {
    var expenseManagement =
        expenseManagementService.getById(expenseDetail.getExpenseManagementId());
    expenseDetail.setYear(expenseManagement.getYear());
    expenseDetailRepository.save(expenseDetail);
  }
  /**
   * xóa một giao dịch chi
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public void delete(Long id) throws FamilyTreeException {
    var expenseDetail = this.getById(id);
    expenseDetailRepository.delete(expenseDetail);
  }
  /**
   * Xem DS các giao dịch chi
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public ExpenseDetailDTO getAll(Long expenseManagementId) throws FamilyTreeException {
    ExpenseDetailDTO expenseDetailDTO = new ExpenseDetailDTO();
    var expenseDetails = expenseDetailRepository.findAllByExpenseManagementId(expenseManagementId);
    expenseDetailDTO.setExpenseDetailList(expenseDetails);
    Long totalMoney = 0L;
    for (ExpenseDetail expenseDetail : expenseDetails) {
      totalMoney += expenseDetail.getExpenseMoney();
    }
    expenseDetailDTO.setTotalMoney(totalMoney);
    return expenseDetailDTO;
  }
  /**
   * Xem thông tin một giao dịch chi theo id
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public ExpenseDetail getById(Long id) throws FamilyTreeException {
    var expenseDetail = expenseDetailRepository.findById(id);
    if (expenseDetail.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.EXPENSE_ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.EXPENSE_ID_IS_NOT_EXIST));
    }
    return expenseDetail.get();
  }
}
