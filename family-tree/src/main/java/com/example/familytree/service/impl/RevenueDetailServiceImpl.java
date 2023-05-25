package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.RevenueDetail;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.RevenueReport;
import com.example.familytree.repository.RevenueDetailRepository;
import com.example.familytree.repository.RevenueManagementRepository;
import com.example.familytree.service.MemberService;
import com.example.familytree.service.RevenueDetailService;
import com.example.familytree.service.RevenueManagementService;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Service
@Builder
public class RevenueDetailServiceImpl implements RevenueDetailService {
  public final RevenueDetailRepository revenueDetailRepository;
  public final RevenueManagementRepository revenueManagementRepository;
  public final MemberService memberService;
  public final RevenueManagementService revenueManagementService;

  /**
   * generate annual revenue detail
   *
   * @author nga
   * @since 25/05/2023
   */
  @Override
  @Transactional
  public RevenueDetail create(RevenueDetail revenueDetail) throws FamilyTreeException {
    var typeOfRevenueSearch = memberService.deAccent(revenueDetail.getTypeOfRevenue());
    revenueDetail.setTypeOfRevenueSearch(typeOfRevenueSearch);
    var revenueManagement = revenueManagementService.getById(revenueDetail.getIdRevenueManagement());
    var realRevenue = revenueManagement.getRealRevenue() + revenueDetail.getMoney();
    revenueManagement.setRealRevenue(realRevenue);
    revenueManagementRepository.save(revenueManagement);
    return revenueDetailRepository.save(revenueDetail);
  }

  /**
   * get all revenue detail
   *
   * @author nga
   * @since 25/05/2023
   */
  @Override
  public List<RevenueDetail> getAll() throws FamilyTreeException {
    return revenueDetailRepository.findAll();
  }
  /**
   * get imformation revenue detail by id
   *
   * @author nga
   * @since 25/05/2023
   */
  @Override
  public RevenueDetail getById(Long id) throws FamilyTreeException {
    var revenueDetail = revenueDetailRepository.findById(id);
    if (revenueDetail.isPresent()) {
      return revenueDetail.get();
    } else {
      throw new FamilyTreeException(
          ExceptionUtils.ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.ID_IS_NOT_EXIST));
    }
  }
  /**
   * update the imformation of the revenue detail
   *
   * @author nga
   * @since 25/05/2023
   */
  @Override
  public void update(RevenueDetail revenueDetail) throws FamilyTreeException {
    // check id has exist
    var id = this.getById(revenueDetail.getId());
    var typeOfRevenueSearch = memberService.deAccent(revenueDetail.getTypeOfRevenue());
    revenueDetail.setTypeOfRevenueSearch(typeOfRevenueSearch);
    revenueDetailRepository.save(revenueDetail);
  }
  /**
   * delete the revenue detail by id
   *
   * @author nga
   * @since 25/05/2023
   */
  @Override
  public void delete(Long id) throws FamilyTreeException {
    revenueDetailRepository.deleteById(id);
  }
  /**
   * create the report of the revenue
   *
   * @author nga
   * @since 25/05/2023
   */
  @Override
  public RevenueReport report(Integer year, String typeOfRevenue) throws FamilyTreeException {
    RevenueReport revenueReport = new RevenueReport();
    Long total = 0L;
    var typeOfRevenueSearch = memberService.deAccent(typeOfRevenue);
    // get list allowance
    var revenueDetailList =
        revenueDetailRepository.getAllByTypeOfRevenue(year, typeOfRevenueSearch);
    for (RevenueDetail revenueDetail : revenueDetailList) {
      total += revenueDetail.getMoney();
    }
    return revenueReport;
  }
}
