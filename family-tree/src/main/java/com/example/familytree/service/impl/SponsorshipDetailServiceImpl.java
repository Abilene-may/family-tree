package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.SponsorsipDetail;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.SponsorshipDetailDTO;
import com.example.familytree.repository.FinancialSponsorshipRepository;
import com.example.familytree.repository.SponsorsipDetailRepository;
import com.example.familytree.service.FinancialSponsorshipService;
import com.example.familytree.service.SponsorshipDetailService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SponsorshipDetailServiceImpl implements SponsorshipDetailService {
  private final SponsorsipDetailRepository sponsorsipDetailRepository;
  private final FinancialSponsorshipService financialSponsorshipService;
  private final FinancialSponsorshipRepository financialSponsorshipRepository;

  /**
   * Thêm một khoản tài trợ trong chi tiết các giao dịch
   *
   * @param sponsorsipDetail
   * @author nga
   * @since 30/05/2023
   */
  @Transactional
  @Override
  public SponsorsipDetail create(SponsorsipDetail sponsorsipDetail) throws FamilyTreeException {
    var financialSponsorship =
        financialSponsorshipService.getById(sponsorsipDetail.getFinancialSponsorshipId());
    // check trạng thái màn ngoài danh sách đã đóng thì không cho thêm mới
    if (financialSponsorship.getStatus().equals(Constant.DA_DONG)) {
      throw new FamilyTreeException(
          ExceptionUtils.E_SPONSORSHIP_DETAIL_1,
          ExceptionUtils.messages.get(ExceptionUtils.E_SPONSORSHIP_DETAIL_1));
    }
    // thiết lập năm cho khoản tài trợ
    sponsorsipDetail.setYear(financialSponsorship.getYear());
    sponsorsipDetailRepository.save(sponsorsipDetail);
    return sponsorsipDetail;
  }
  /**
   * Sửa một khoản tài trợ trong chi tiết các giao dịch
   *
   * @param sponsorsipDetail
   * @author nga
   * @since 30/05/2023
   */
  @Transactional
  @Override
  public void update(SponsorsipDetail sponsorsipDetail) throws FamilyTreeException {
    var financialSponsorship =
        financialSponsorshipService.getById(sponsorsipDetail.getFinancialSponsorshipId());
    // check trạng thái màn ngoài danh sách đã đóng thì không cho sửa
    if (financialSponsorship.getStatus().equals(Constant.DA_DONG)) {
      throw new FamilyTreeException(
          ExceptionUtils.E_SPONSORSHIP_DETAIL_1,
          ExceptionUtils.messages.get(ExceptionUtils.E_SPONSORSHIP_DETAIL_1));
    }
    // thiết lập năm cho khoản tài trợ
    sponsorsipDetail.setYear(financialSponsorship.getYear());
    sponsorsipDetailRepository.save(sponsorsipDetail);
  }

  /**
   * Xóa một khoản tài trợ trong chi tiết các giao dịch
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public void delete(Long id) throws FamilyTreeException {
    var sponsorsipDetail = this.getById(id);
    sponsorsipDetailRepository.delete(sponsorsipDetail);
  }
  /**
   * Xem danh sách khoản tài trợ trong chi tiết các giao dịch
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public SponsorshipDetailDTO getAll(Long financialSponsorshipId) throws FamilyTreeException {
    SponsorshipDetailDTO sponsorshipDetailDTO = new SponsorshipDetailDTO();
    List<SponsorsipDetail> sponsorsipDetails =
        sponsorsipDetailRepository.findAllByFinancialSponsorshipId(financialSponsorshipId);
    Long totalMoney = sponsorsipDetails.stream().mapToLong(SponsorsipDetail::getSponsorshipMoney).sum();
    sponsorshipDetailDTO.setSponsorsipDetailList(sponsorsipDetails);
    sponsorshipDetailDTO.setTotalMoney(totalMoney);
    return sponsorshipDetailDTO;
  }
  /**
   * Tìm khoản tài trợ trong chi tiết các giao dịch by id
   *
   * @author nga
   * @since 30/05/2023
   */
  @Override
  public SponsorsipDetail getById(Long id) throws FamilyTreeException {
    var sponsorsipDetail = sponsorsipDetailRepository.findById(id);
    if (sponsorsipDetail.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.ID_IS_NOT_EXIST));
    }
    return sponsorsipDetail.get();
  }

  public SponsorshipDetailServiceImpl(
      @Lazy FinancialSponsorshipService financialSponsorshipService,
      @Lazy SponsorsipDetailRepository sponsorsipDetailRepository,
      @Lazy FinancialSponsorshipRepository financialSponsorshipRepository) {
    super();
    this.financialSponsorshipService = financialSponsorshipService;
    this.sponsorsipDetailRepository = sponsorsipDetailRepository;
    this.financialSponsorshipRepository = financialSponsorshipRepository;
  }
}
