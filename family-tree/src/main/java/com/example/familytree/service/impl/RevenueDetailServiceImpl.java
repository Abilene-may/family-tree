package com.example.familytree.service.impl;

import com.example.familytree.domain.RevenueDetail;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.RevenueDetailDTO;
import com.example.familytree.repository.RevenueDetailRepository;
import com.example.familytree.service.MemberService;
import com.example.familytree.service.RevenueDetailService;
import com.example.familytree.service.RevenueManagementService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RevenueDetailServiceImpl implements RevenueDetailService {
  public final RevenueDetailRepository revenueDetailRepository;
  public final MemberService memberService;
  public final RevenueManagementService revenueManagementService;

  /**
   * Lấy tất cả revenue detail qua id của revenueMangement
   * @author nga
   */
  @Override
  public List<RevenueDetail> getAllByIdRevenueManagement(Long idRevenueManagemnet) throws FamilyTreeException {
    var revenueDetailList = revenueDetailRepository.findAllByIdRevenueManagement(idRevenueManagemnet);
    if(revenueDetailList.isEmpty()){
      List<RevenueDetail> revenueDetails = new ArrayList<>();
      // lấy tất cả thành viên tuổi từ 18->60
      var memberList = memberService.findAllAgeInTheRange();
      memberList.stream()
          .forEach(
              member -> {
                RevenueDetail revenueDetail = new RevenueDetail();
                revenueDetail.setId(member.getId());
                // set người đóng tiền
                revenueDetail.setPayer(member.getFullName());
                // thiết lập số tiền cần đóng của mỗi thành viên
                try {
                  var revenueManagement = revenueManagementService.getById(idRevenueManagemnet);
                  revenueDetail.setMoney(revenueManagement.getRevenuePerPerson());
                  revenueDetail.setYear(revenueManagement.getYear());
                } catch (FamilyTreeException e) {
                  throw new RuntimeException(e);
                }
                // set trạng thái
                revenueDetail.setStatus(false);
                revenueDetail.setIdRevenueManagement(idRevenueManagemnet);
                revenueDetails.add(revenueDetail);
                revenueDetailRepository.save(revenueDetail);
              });
      return revenueDetails;
    } else {
      return revenueDetailList;
    }
  }

  /**
   * Sửa trạng thái và ngày đóng
   *
   * @author nga
   */
  @Override
  public void update(RevenueDetailDTO revenueDetailDTO) throws FamilyTreeException {
    var optionalRevenueDetail = revenueDetailRepository.findById(revenueDetailDTO.getId());
    if (optionalRevenueDetail.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.ID_IS_NOT_EXIST));
    }
    var revenueDetail = optionalRevenueDetail.get();
    revenueDetail.setDate(revenueDetailDTO.getDate());
    revenueDetail.setStatus(revenueDetailDTO.getStatus());
    revenueDetailRepository.save(revenueDetail);
  }

  public RevenueDetailServiceImpl(
      @Lazy RevenueManagementService revenueManagementService,
      @Lazy RevenueDetailRepository revenueDetailRepository,
      @Lazy MemberService memberService) {
    super();
    this.revenueDetailRepository = revenueDetailRepository;
    this.memberService = memberService;
    this.revenueManagementService = revenueManagementService;
  }
}
