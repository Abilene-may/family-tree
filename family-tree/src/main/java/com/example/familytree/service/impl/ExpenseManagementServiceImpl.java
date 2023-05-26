package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.ExpenseManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.repository.ExpenseManagementRepository;
import com.example.familytree.service.ExpenseManagementService;
import com.example.familytree.service.MemberService;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class ExpenseManagementServiceImpl implements ExpenseManagementService {
  private final ExpenseManagementRepository expenseManagementRepository;
  private final MemberService memberService;

  /**
   * create a expense management
   *
   * @author nga
   * @since 26/05/2023
   */
  @Override
  public ExpenseManagement create(ExpenseManagement expenseManagement) throws FamilyTreeException {
    LocalDate date = LocalDate.now();
    Integer yearNow = date.getYear();
    if (expenseManagement.getYear() < yearNow
        && expenseManagement.getStatus().equals(Constant.DANG_MO)) {
      throw new FamilyTreeException(
          ExceptionUtils.CLOSED_EXPENSE,
          ExceptionUtils.messages.get(ExceptionUtils.CLOSED_EXPENSE));
    }
    var management = expenseManagementRepository.save(expenseManagement);
    return management;
  }

  /**
   * get list expense management
   *
   * @author nga
   * @since 26/05/2023
   */
  @Override
  public List<ExpenseManagement> getAll() throws FamilyTreeException {
    var expenseManagements = expenseManagementRepository.findAll();
    return expenseManagements;
  }

  /**
   * get expense management by id
   *
   * @author nga
   * @since 26/05/2023
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

  @Override
  public void update(ExpenseManagement expenseManagement) throws FamilyTreeException {
    var management = this.getById(expenseManagement.getId());
    var status = memberService.deAccent(management.getStatus());
    // check status is "Da dong", don't allow update
    if(status.equals(Constant.DA_DONG)){
      throw new FamilyTreeException(
          ExceptionUtils.CLOSED_REVENUE,
          ExceptionUtils.messages.get(ExceptionUtils.CLOSED_REVENUE));
    }
    expenseManagementRepository.save(expenseManagement);
  }
}
