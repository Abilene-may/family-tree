package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.FinancialSponsorship;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.FinancialSponsorshipReport;
import com.example.familytree.repository.FinancialSponsorshipRepository;
import com.example.familytree.service.FinancialSponsorshipService;
import com.example.familytree.service.SponsorshipDetailService;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Service xử lý logic quản lý tài chính */
@Service
@RequiredArgsConstructor
@Builder
public class FinancialSponorshipServiceImpl implements FinancialSponsorshipService {
  private final FinancialSponsorshipRepository financialSponsorshipRepository;
  private final SponsorshipDetailService sponsorshipDetailService;

  /**
   * Tạo mới đợt tài trợ trong năm
   *
   * @param financialSponsorship
   * @author nga
   * @since 29/05/2023
   */
  @Override
  public FinancialSponsorship create(FinancialSponsorship financialSponsorship)
      throws FamilyTreeException {
    // check TH nhập ngày bắt đầu sau ngày đóng
    if (financialSponsorship.getStartDate().isAfter(financialSponsorship.getEndDate())) {
      throw new FamilyTreeException(
          ExceptionUtils.E_FINANCIAL_SPONSORSHIP_1,
          ExceptionUtils.messages.get(ExceptionUtils.E_FINANCIAL_SPONSORSHIP_1));
    }
    // check TH ngày hiện tại sau ngày đóng nhưng trạng thái <> "Đã đóng"
    LocalDate today = LocalDate.now();
    if (today.isAfter(financialSponsorship.getEndDate())
        && !financialSponsorship.getStatus().equals(Constant.DA_DONG)) {
      throw new FamilyTreeException(
          ExceptionUtils.E_FINANCIAL_SPONSORSHIP_2,
          ExceptionUtils.messages.get(ExceptionUtils.E_FINANCIAL_SPONSORSHIP_2));
    }
    var sponsorship = financialSponsorshipRepository.save(financialSponsorship);
    return sponsorship;
  }
  /**
   * Sửa đợt tài trợ trong năm
   *
   * @param financialSponsorship
   * @author nga
   * @since 29/05/2023
   */
  @Override
  public void update(FinancialSponsorship financialSponsorship) throws FamilyTreeException {
    var sponsorship = this.getById(financialSponsorship.getId());
    // check TH nhập ngày bắt đầu sau ngày đóng
    if (financialSponsorship.getStartDate().isAfter(financialSponsorship.getEndDate())) {
      throw new FamilyTreeException(
          ExceptionUtils.E_FINANCIAL_SPONSORSHIP_1,
          ExceptionUtils.messages.get(ExceptionUtils.E_FINANCIAL_SPONSORSHIP_1));
    }
    // check TH ngày hiện tại sau ngày đóng nhưng trạng thái <> "Đã đóng"
    LocalDate today = LocalDate.now();
    if (today.isAfter(financialSponsorship.getEndDate())
        && !financialSponsorship.getStatus().equals(Constant.DA_DONG)) {
      throw new FamilyTreeException(
          ExceptionUtils.E_FINANCIAL_SPONSORSHIP_2,
          ExceptionUtils.messages.get(ExceptionUtils.E_FINANCIAL_SPONSORSHIP_2));
    }
    financialSponsorshipRepository.save(financialSponsorship);
  }

  /**
   * Xem danh sách đợt tài trợ
   *
   * @author nga
   * @since 29/05/2023
   */
  @Override
  public List<FinancialSponsorship> getAll() throws FamilyTreeException {
    return financialSponsorshipRepository.findAll();
  }

  /**
   * Tìm đợt tài trợ theo id
   *
   * @author nga
   * @since 29/05/2023
   */
  @Override
  public FinancialSponsorship getById(Long id) throws FamilyTreeException {
    var financialSponsorship = financialSponsorshipRepository.findById(id);
    if (financialSponsorship.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.ID_IS_NOT_EXIST));
    }
    return financialSponsorship.get();
  }

  /**
   * Báo cáo tiền tài trợ theo năm
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public FinancialSponsorshipReport report(Integer year) throws FamilyTreeException {
    FinancialSponsorshipReport financialSponsorshipReport = new FinancialSponsorshipReport();
    var financialSponsorships = financialSponsorshipRepository.findAllByYear(year);
    financialSponsorshipReport.setFinancialSponsorships(financialSponsorships);
    Long totalMoney = 0L;
    for (FinancialSponsorship financialSponsorship: financialSponsorships) {
      // Lấy tổng tiền từ danh sách giao dịch
        var sponsorshipDetailDTO = sponsorshipDetailService.getAll(financialSponsorship.getId());
        totalMoney += sponsorshipDetailDTO.getTotalMoney();
    }
    financialSponsorshipReport.setTotalMoney(totalMoney);
    return null;
  }
}
