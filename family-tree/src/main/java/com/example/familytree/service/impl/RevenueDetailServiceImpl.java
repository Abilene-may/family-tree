package com.example.familytree.service.impl;

import com.example.familytree.domain.RevenueDetail;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.RevenueDetailDTO;
import com.example.familytree.repository.RevenueDetailRepository;
import com.example.familytree.service.MemberService;
import com.example.familytree.service.RevenueDetailService;
import com.example.familytree.service.RevenueManagementService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RevenueDetailServiceImpl implements RevenueDetailService {
  public final RevenueDetailRepository revenueDetailRepository;
  public final MemberService memberService;
  public final RevenueManagementService revenueManagementService;

  public RevenueDetailServiceImpl(
      @Lazy RevenueManagementService revenueManagementService,
      @Lazy RevenueDetailRepository revenueDetailRepository,
      @Lazy MemberService memberService) {
    super();
    this.revenueDetailRepository = revenueDetailRepository;
    this.memberService = memberService;
    this.revenueManagementService = revenueManagementService;
  }

  /**
   * Thiết lập DS những người cần đóng cho 1 khoản thu hằng năm
   *
   * @author nga
   */
  @Override
  public List<RevenueDetail> getAllByIdRevenueManagement(
      Long idRevenueManagemnet, LocalDate startDate) throws FamilyTreeException {
    // Lấy thông tin khoản thu
    var revenueManagement = revenueManagementService.getById(idRevenueManagemnet);
    // Kiểm tra xem đã thiết lập DS các thành viên cần đóng tiền chưa
    // Lấy list các giao dịch thu của một khoản thu theo idRevenueManagemnet truyền vào
    var revenueDetailList =
        revenueDetailRepository.findAllByIdRevenueManagement(idRevenueManagemnet);


    // Lấy thông tin ngày hiện tại
    LocalDate today = LocalDate.now();
    // Nếu chưa thiết lập các giao dịch thu và ngày bắt đầu thu (của khoản thu hằng năm) < ngày hiện tại
    // TH ngày bắt đầu thu > ngày hiện tại và số tiền đã thay đổi
    // => không cho phép thiết lập DS các thành viên cần đóng tiền
    if (revenueDetailList.size() == 0
        || (revenueDetailList.size() != 0
            && !revenueManagement.getRevenuePerPerson().equals(revenueDetailList.get(0).getMoney())
            && today.isAfter(startDate))) {
      List<RevenueDetail> revenueDetails = new ArrayList<>();
      // lấy tất cả thành viên tuổi từ 18->60
      var memberList = memberService.findAllAgeInTheRange();
      memberList.stream()
          .forEach(
              member -> {
                RevenueDetail revenueDetail = new RevenueDetail();
                revenueDetail.setIdMember(member.getId());
                // thiết lập tên người đóng tiền
                revenueDetail.setPayer(member.getFullName());
                // thiết lập số tiền cần đóng của mỗi thành viên
                revenueDetail.setMoney(revenueManagement.getRevenuePerPerson());
                revenueDetail.setYear(revenueManagement.getYear());
                // set trạng thái mặc định ban đầu là chưa thu
                revenueDetail.setStatus(false);
                revenueDetail.setIdRevenueManagement(idRevenueManagemnet);
                revenueDetails.add(revenueDetail);
                revenueDetailRepository.save(revenueDetail);
              });
      return revenueDetails;
    } else {
      // TH đã có DS các thành viên đóng tiền trong database
      // Trả về DS các thành viên cần đóng đã được thiết lập trước đó
      return revenueDetailList;
    }
  }

  /**
   * Sửa trạng thái thành đã đóng
   *
   * @author nga
   */
  @Override
  @Transactional
  public void update(RevenueDetailDTO revenueDetailDTO) throws FamilyTreeException {
    // Tìm bản ghi cần sửa trong database
    var revenueDetail = this.getById(revenueDetailDTO.getId());
    // Trả ra lỗi nếu không tìm thấy bản ghi trong database
    if (revenueDetail.getIdRevenueManagement().equals(revenueDetailDTO.getIdRevenueManagemnet())) {
      throw new FamilyTreeException(
          ExceptionUtils.REVENUE_ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.REVENUE_ID_IS_NOT_EXIST));
    }
    LocalDate today = LocalDate.now();
    // TH ko nhập ngày Lưu thông tin ngày đóng là ngày hiện tại - ngày sửa
    if(revenueDetailDTO.getDate() == null){
      revenueDetail.setDate(today);
    }
    revenueDetail.setStatus(revenueDetailDTO.getStatus());
    // Lưu lại thông tin đã đóng vào database
    revenueDetailRepository.save(revenueDetail);
  }

  /**
   * Lấy thông tin một khoản thu theo id
   * @param id
   * @return
   * @throws FamilyTreeException
   */
  @Override
  public RevenueDetail getById(Long id) throws FamilyTreeException {
    var optionalRevenueDetail = revenueDetailRepository.findById(id);
    if (optionalRevenueDetail.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.REVENUE_DETAIL_ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.REVENUE_DETAIL_ID_IS_NOT_EXIST));
    }
    return optionalRevenueDetail.get();
  }


}
