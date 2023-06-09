package com.example.familytree.service.impl;

import com.example.familytree.domain.FinancialSponsorship;
import com.example.familytree.domain.SponsorsipDetail;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.FinancialSponsorshipReport;
import com.example.familytree.repository.FinancialSponsorshipRepository;
import com.example.familytree.repository.SponsorsipDetailRepository;
import com.example.familytree.service.FinancialSponsorshipService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/** Service xử lý logic quản lý tài chính */
@Service
@Slf4j
public class FinancialSponorshipServiceImpl implements FinancialSponsorshipService {
  private final FinancialSponsorshipRepository financialSponsorshipRepository;
  private final SponsorsipDetailRepository sponsorsipDetailRepository;

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
    // check TH ngày hiện tại sau ngày đóng nhưng trạng thái <> "Đã đóng" true
    LocalDate today = LocalDate.now();
    if (today.isAfter(financialSponsorship.getEndDate())
        && !financialSponsorship.getStatus().equals(false)) {
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
        && !financialSponsorship.getStatus().equals(false)) {
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
          ExceptionUtils.SPONSORSHIP_ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.SPONSORSHIP_ID_IS_NOT_EXIST));
    }
    return financialSponsorship.get();
  }

  /**
   * Báo cáo tiền tài trợ từ ngày đến ngày
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public FinancialSponsorshipReport report(LocalDate effectiveStartDate, LocalDate effectiveEndDate)
      throws FamilyTreeException {
    // Tìm kiếm danh sách các khoản tài trợ từ ngày đến ngày
    FinancialSponsorshipReport financialSponsorshipReport = new FinancialSponsorshipReport();
    List<SponsorsipDetail> sponsorsipDetails = new ArrayList<>();
    if(effectiveStartDate != null && effectiveEndDate != null){
      sponsorsipDetails =
          sponsorsipDetailRepository.findAllByStartDateAndEndDate(
              effectiveStartDate, effectiveEndDate);
    } else if (effectiveStartDate != null){
      sponsorsipDetails =
          sponsorsipDetailRepository.findAllByStartDate(
              effectiveStartDate);
    } else if (effectiveEndDate != null){
      sponsorsipDetails =
          sponsorsipDetailRepository.findAllByEndDate(
              effectiveEndDate);
    } else {
      sponsorsipDetails = sponsorsipDetailRepository.findAll();
    }

    financialSponsorshipReport.setSponsorsipDetails(sponsorsipDetails);
    Long totalMoney = 0L;
    for (SponsorsipDetail sponsorsipDetail : sponsorsipDetails) {
      // Tính tổng tiền
      totalMoney += sponsorsipDetail.getSponsorshipMoney();
    }
    financialSponsorshipReport.setTotalMoney(totalMoney);
    return financialSponsorshipReport;
  }
  /**
   * Xóa một quản lý tiền tài trợ
   *
   * @author nga
   * @since 06/06/2023
   */
  @Override
  public void delete(Long id) throws FamilyTreeException {
    var financialSponsorship = this.getById(id);
    financialSponsorshipRepository.delete(financialSponsorship);
    // Xóa chi tiết các giao dịch của đợt tài trợ
    var sponsorsipDetails = sponsorsipDetailRepository.findAllByFinancialSponsorshipId(id);
    sponsorsipDetailRepository.deleteAll(sponsorsipDetails);
  }

  public FinancialSponorshipServiceImpl(
      @Lazy FinancialSponsorshipRepository financialSponsorshipRepository,
      @Lazy SponsorsipDetailRepository sponsorsipDetailRepository) {
    super();
    this.financialSponsorshipRepository = financialSponsorshipRepository;
    this.sponsorsipDetailRepository = sponsorsipDetailRepository;
  }
}
