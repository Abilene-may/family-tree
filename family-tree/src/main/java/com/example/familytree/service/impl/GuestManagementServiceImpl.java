package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.GuestManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.guestmanagement.GuestManagementReqDTO;
import com.example.familytree.repository.GuestManagementRepository;
import com.example.familytree.repository.MemberRepository;
import com.example.familytree.service.GuestManagementService;
import com.example.familytree.service.MemberService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestManagementServiceImpl implements GuestManagementService {
  public final GuestManagementRepository guestManagementRepository;
  public final MemberService memberService;
  public final MemberRepository memberRepository;

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
    var listCheck = this.findAllByEventManagementId(reqDTO.getEventManagamentId()) ;
    List<GuestManagement> response = new ArrayList<>();
    if(reqDTO.getChooseAll() && listCheck.isEmpty()){
      var memberList = memberRepository.findAllByStatus(Constant.DA_MAT);
      List<GuestManagement> guestManagements = new ArrayList<>();
      // ánh xạ từ entity sang DTO
      List<GuestManagement> guestManagementList =
          memberList.stream()
              .map(
                  x -> {
                    GuestManagement guestManagement = new GuestManagement();
                    guestManagement.setMemberId(x.getId());
                    guestManagement.setFullName(x.getFullName());
                    guestManagement.setGender(x.getGender());
                    guestManagement.setDateOfBirth(x.getDateOfBirth());
                    guestManagement.setMobilePhoneNumber(x.getMobilePhoneNumber());
                    guestManagement.setCareer(x.getCareer());
                    guestManagement.setEventManagementId(reqDTO.getEventManagamentId());
                    guestManagements.add(guestManagement);
                    return guestManagement;
                  })
              .collect(Collectors.toList());
      guestManagementRepository.saveAll(guestManagements);
      response.addAll(guestManagementList);
      return response;
    }
    return listCheck;
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
          ExceptionUtils.ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.ID_IS_NOT_EXIST));
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
    var guestManagements  = guestManagementRepository.findAllByEventManagementId(eventManagementId);
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
    var guestManagements  = guestManagementRepository.findAll();
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
  public List<GuestManagement> findAllByEventManagementId(Long eventManagementId) throws FamilyTreeException {
    var guestManagements = guestManagementRepository.findAllByEventManagementId(eventManagementId);
    if (guestManagements.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.ID_IS_NOT_EXIST));
    }
    return guestManagements;
  }
}
