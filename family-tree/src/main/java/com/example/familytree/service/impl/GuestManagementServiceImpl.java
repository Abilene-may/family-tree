package com.example.familytree.service.impl;

import com.amazonaws.util.StringUtils;
import com.example.familytree.commons.Constant;
import com.example.familytree.domain.GuestManagement;
import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.guestmanagement.GuestManagementReqDTO;
import com.example.familytree.repository.GuestManagementRepository;
import com.example.familytree.repository.MemberRepository;
import com.example.familytree.service.EventManagementService;
import com.example.familytree.service.GuestManagementService;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


/** Service xử lý logic quản lý khách mời */
@Service
@Slf4j
public class GuestManagementServiceImpl implements GuestManagementService {
  public final GuestManagementRepository guestManagementRepository;
  public final EventManagementService eventManagementService;
  public final MemberRepository memberRepository;

  public GuestManagementServiceImpl(
      @Lazy GuestManagementRepository guestManagementRepository,
      @Lazy EventManagementService eventManagementService,
      @Lazy MemberRepository memberRepository) {
    super();
    this.guestManagementRepository = guestManagementRepository;
    this.eventManagementService = eventManagementService;
    this.memberRepository = memberRepository;
  }

  /**
   * Thiết lập danh sách khách mời lọc theo dữ liệu đầu vào
   *
   * @param reqDTO
   * @return
   * @throws FamilyTreeException
   * @author nga
   * @since 16/06/2023
   */
  @Override
  public List<GuestManagement> setUpListGuest(GuestManagementReqDTO reqDTO)
      throws FamilyTreeException {
    var listCheck =
        guestManagementRepository.findAllByEventManagementId(reqDTO.getEventManagementId());
    var eventManagement = eventManagementService.getById(reqDTO.getEventManagementId());
    if (eventManagement.getStatus().equals(Constant.DA_DONG)) {
      throw new FamilyTreeException(
          ExceptionUtils.E_EVENT_IS_CLOSED,
          ExceptionUtils.messages.get(ExceptionUtils.E_EVENT_IS_CLOSED));
    }
    LocalDate today = LocalDate.now();
    if (listCheck.isEmpty() || today.isBefore(eventManagement.getEventDate())) {
      this.deleteAll(eventManagement.getId());
      List<GuestManagement> guestManagementList = new ArrayList<>();
      List<GuestManagement> response = new ArrayList<>();
      if (reqDTO.getChooseAll()) {
        var memberList = memberRepository.findAllByStatus(Constant.DA_MAT);
        // ánh xạ từ entity sang DTO
        guestManagementList = this.guestManagementList(reqDTO, memberList);
        response.addAll(guestManagementList);
        guestManagementRepository.saveAll(guestManagementList);
        return response;
      }
      List<Member> memberList = new ArrayList<>();
      // TH ko tìm theo giới tính
      if (StringUtils.isNullOrEmpty(reqDTO.getGender()) || reqDTO.getGender().equals(Constant.TAT_CA)) {
        memberList = getMemberList(reqDTO, today, memberList);
        guestManagementList = this.guestManagementList(reqDTO, memberList);
        response.addAll(guestManagementList);
        guestManagementRepository.saveAll(guestManagementList);
        return response;
      }
      memberList = searchMembers(reqDTO, today, memberList);
      guestManagementList = this.guestManagementList(reqDTO, memberList);
      response.addAll(guestManagementList);
      guestManagementRepository.saveAll(guestManagementList);
      return response;
    }
    return listCheck;
  }

  /**
   *  Thêm mới một khách mời
   *
   * @param guestManagement
   * @return
   * @throws FamilyTreeException
   * @since 05/07/2023
   */
  @Override
  public GuestManagement createGuest(GuestManagement guestManagement) throws FamilyTreeException {
    LocalDate today = LocalDate.now();
    // Lấy thông tin của sự kiện
    var eventManagement = eventManagementService.getById(guestManagement.getEventManagementId());
    if (eventManagement.getStatus().equals(Constant.DA_DONG)) {
      throw new FamilyTreeException(
          ExceptionUtils.E_EVENT_IS_CLOSED,
          ExceptionUtils.messages.get(ExceptionUtils.E_EVENT_IS_CLOSED));
    }
    return guestManagementRepository.save(guestManagement);
  }

  /**
   * validate tách hàm thiết lập khách mời
   *
   * @param reqDTO
   * @param today
   * @param memberList
   */
  private List<Member> getMemberList(GuestManagementReqDTO reqDTO, LocalDate today,
      List<Member> memberList) {
    // TH ko truyền vào tuổi bắt đầu
    if (reqDTO.getStartAge() == null) {
      memberList = this.memberListByAge(0, reqDTO.getEndAge());
    }
    // TH ko truyền vào tuổi kết thúc
    if (reqDTO.getEndAge() == null) {
      LocalDate dateOfBirthMax = memberRepository.findByDateOfBirth(Constant.DA_MAT);
      Period period = Period.between(dateOfBirthMax, today);
      Integer ageMax = period.getYears();
      memberList = this.memberListByAge(reqDTO.getStartAge(), ageMax);
    }
    // TH truyền cả từ tuổi -> tuổi
    if (reqDTO.getStartAge() != null && reqDTO.getEndAge() != null) {
      memberList = this.memberListByAge(reqDTO.getStartAge(), reqDTO.getEndAge());
    }
    return memberList;
  }

  /**
   * Hàm tách method thiết lập khách mời
   *
   * @param reqDTO
   * @param today
   * @param memberList
   * @author nga
   */
  private List<Member> searchMembers(
      GuestManagementReqDTO reqDTO, LocalDate today, List<Member> memberList) {
    // TH không tìm theo độ tuổi
    if (reqDTO.getStartAge() == null && reqDTO.getEndAge() == null) {
      memberList = memberRepository.findAllGuestByGender(reqDTO.getGender(), Constant.DA_MAT);
    }
    // TH tìm theo cả hai nhưng startAge == null
    else if (reqDTO.getStartAge() == null) {
      var endDate = today.minusYears(0);
      var startDate = today.minusYears(reqDTO.getEndAge());
      memberList =
          memberRepository.findAllByDateOfBirthAndGender(
              startDate, endDate, reqDTO.getGender(), Constant.DA_MAT);
    }
    // TH tìm theo cả hai nhưng endAge == null
    else if (reqDTO.getEndAge() == null) {
      LocalDate dateOfBirthMax = memberRepository.findByDateOfBirth(Constant.DA_MAT);
      Period period = Period.between(dateOfBirthMax, today);
      Integer ageMax = period.getYears();
      var endDate = today.minusYears(reqDTO.getStartAge());
      var startDate = today.minusYears(ageMax);
      memberList =
          memberRepository.findAllByDateOfBirthAndGender(
              startDate, endDate, reqDTO.getGender(), Constant.DA_MAT);
    } else {
      var endDate = today.minusYears(reqDTO.getStartAge());
      var startDate = today.minusYears(reqDTO.getEndAge());
      // TH tìm theo tất cả điều kiện đầu vào
      memberList =
          memberRepository.findAllByDateOfBirthAndGender(
              startDate , endDate, reqDTO.getGender(), Constant.DA_MAT);
    }
    return memberList;
  }

  public List<GuestManagement> guestManagementList(
      GuestManagementReqDTO reqDTO, List<Member> members) {
    // ánh xạ từ entity sang DTO
    List<GuestManagement> guestManagementList =
        members.stream()
            .map(
                x -> {
                  GuestManagement guestManagement = new GuestManagement();
                  guestManagement.setMemberId(x.getId());
                  guestManagement.setFullName(x.getFullName());
                  guestManagement.setGender(x.getGender());
                  guestManagement.setDateOfBirth(x.getDateOfBirth());
                  guestManagement.setMobilePhoneNumber(x.getMobilePhoneNumber());
                  guestManagement.setCareer(x.getCareer());
                  guestManagement.setEventManagementId(reqDTO.getEventManagementId());
                  return guestManagement;
                })
            .toList();
    return guestManagementList;
  }

  /**
   * Tách hàm tìm kiếm theo tuổi của thành viên
   *
   * @param startAge
   * @param endAge
   * @return
   * @author nga
   */
  public List<Member> memberListByAge(Integer startAge, Integer endAge) {
    LocalDate today = LocalDate.now();
    var endDate = today.minusYears(startAge);
    var startDate = today.minusYears(endAge);
    List<Member> membersByAge =
        memberRepository.findAllAgeInTheRange(endDate, startDate, Constant.DA_MAT);
    return membersByAge;
  }

  /**
   * Xóa một khách mời
   *
   * @param id
   * @throws FamilyTreeException
   * @author nga
   * @since 16/06/2023
   */
  @Override
  public void delete(Long id) throws FamilyTreeException {
    var guestManagement = guestManagementRepository.findById(id);
    if (guestManagement.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.GUEST_ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.GUEST_ID_IS_NOT_EXIST));
    }
    guestManagementRepository.delete(guestManagement.get());
  }

  /**
   * Xóa toàn bộ khách mời của một sự kiện
   *
   * @param eventManagementId
   * @throws FamilyTreeException
   * @author nga
   * @since 16/06/2023
   */
  @Override
  public void deleteAll(Long eventManagementId) throws FamilyTreeException {
    var guestManagements = guestManagementRepository.findAllByEventManagementId(eventManagementId);
    guestManagementRepository.deleteAll(guestManagements);
  }

  /**
   * DS tất cả các khách mời không theo sự kiện
   *
   * @return
   * @throws FamilyTreeException
   * @author nga
   * @since 16/06/2023
   */
  @Override
  public List<GuestManagement> getAll() throws FamilyTreeException {
    var guestManagements = guestManagementRepository.findAll();
    return guestManagements;
  }

  /**
   * DS khách mời trong một sự kiện
   *
   * @param eventManagementId
   * @return
   * @throws FamilyTreeException
   * @author nga
   * @since 16/06/2023
   */
  @Override
  public List<GuestManagement> findAllByEventManagementId(Long eventManagementId)
      throws FamilyTreeException {
    // check thông tin sự kiện theo đầu vào
    var eventManagement = eventManagementService.getById(eventManagementId);
    var guestManagementList = guestManagementRepository.findAllByEventManagementId(eventManagementId);
    if (guestManagementList.size() ==0) {
      throw new FamilyTreeException(
          ExceptionUtils.GUEST_MANAGEMENT_IS_EMPTY,
          ExceptionUtils.messages.get(ExceptionUtils.GUEST_MANAGEMENT_IS_EMPTY));
    }
    return guestManagementList;
  }

}
