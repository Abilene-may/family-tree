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
   * generate annual revenue
   *
   * @author nga
   * @since 25/05/2023
   */
  @Override
  public RevenueManagement create(RevenueManagement revenueManagement) throws FamilyTreeException {
    // set status search
    var status = memberService.deAccent(revenueManagement.getStatus());
    // check today after due date and status is in progress
    LocalDate localDate = LocalDate.now();
    var dueDate = revenueManagement.getDueDate();
    if (localDate.isAfter(dueDate) && status.equals(Constant.DANG_MO)) {
      throw new FamilyTreeException(
          ExceptionUtils.WRONG_STATUS_REVENUE,
          ExceptionUtils.messages.get(ExceptionUtils.WRONG_STATUS_REVENUE));
    }
    revenueManagement.setRealRevenue(0L);
    revenueManagement.setStatusSearch(status);
    return revenueManagementRepository.save(revenueManagement);
  }

  /**
   * get all annual revenues
   *
   * @author nga
   * @since 25/05/2023
   */
  @Override
  public List<RevenueManagement> getAll() throws FamilyTreeException {
    var revenueManagements = revenueManagementRepository.findAll();
    return revenueManagements;
  }

  /**
   * get imformation revenue by id
   *
   * @author nga
   * @since 25/05/2023
   */
  @Override
  public RevenueManagement getById(Long id) throws FamilyTreeException {
    var revenueManagement = revenueManagementRepository.findById(id);
    if (revenueManagement.isPresent()) {
      return revenueManagement.get();
    } else {
      throw new FamilyTreeException(
          ExceptionUtils.ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.ID_IS_NOT_EXIST));
    }
  }

  /**
   * update the imformation of the revenue
   *
   * @author nga
   * @since 25/05/2023
   */
  @Override
  public void update(RevenueManagement revenueManagement) throws FamilyTreeException {
    // check input
    var management = this.getById(revenueManagement.getId());
    // set status search
    var status = memberService.deAccent(revenueManagement.getStatus());
    // check status
    if(status.equals(Constant.DA_DONG)){
      throw new FamilyTreeException(
          ExceptionUtils.CLOSED_REVENUE,
          ExceptionUtils.messages.get(ExceptionUtils.CLOSED_REVENUE));
    }
    // check today after due date and status is in progress
    LocalDate localDate = LocalDate.now();
    if (localDate.isAfter(revenueManagement.getDueDate())
        && status.equals(Constant.DANG_MO)) {
      throw new FamilyTreeException(
          ExceptionUtils.WRONG_STATUS_REVENUE,
          ExceptionUtils.messages.get(ExceptionUtils.WRONG_STATUS_REVENUE));
    }
    revenueManagement.setStatusSearch(status);
    revenueManagementRepository.save(revenueManagement);
  }
}
