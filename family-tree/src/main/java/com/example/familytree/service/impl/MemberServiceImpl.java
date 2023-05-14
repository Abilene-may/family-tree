package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.MemberDTO;
import com.example.familytree.repository.MemberRepository;
import com.example.familytree.service.MemberService;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Builder
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;

  /**
   * đăng nhập vào tài khoản
   *
   * @param userName, password
   * @author nga
   */
  @Override
  public Member logIn(String userName, String password) throws FamilyTreeException {
    if (userName == null || password == null) {
      throw new FamilyTreeException(
          ExceptionUtils.USER_LOGIN_1, ExceptionUtils.messages.get(ExceptionUtils.USER_LOGIN_1));
    } else {
      var memberOptional = memberRepository.findByUserName(userName);
      var member = memberOptional.get();
      if (memberOptional.isEmpty()) {
        throw new FamilyTreeException(
            ExceptionUtils.USER_LOGIN_2, ExceptionUtils.messages.get(ExceptionUtils.USER_LOGIN_2));
      }
      if (!password.equals(member.getPassword())) {
        throw new FamilyTreeException(
            ExceptionUtils.USER_LOGIN_3, ExceptionUtils.messages.get(ExceptionUtils.USER_LOGIN_3));
      }
      return member;
    }
  }
  /**
   * Lấy ra danh sách các thành viên trong gia phả
   *
   * @author nga
   */
  @Override
  public MemberDTO getAllMember() {
    MemberDTO memberDTO = new MemberDTO();
    var members = memberRepository.findAll();
    memberDTO.setMembers(members);
    memberDTO.setCount(members.size());
    return memberDTO;
  }

  /**
   * thêm thành viên mới đồng thời cấp user
   *
   * @author nga
   */
  @Override
  @Transactional
  public void createMember(Member member) throws FamilyTreeException {
    var memberDTO = this.getAllMember();
    // set đời cho thành viên
    if (memberDTO.getCount() == 0) {
      member.setGeneration(1);
    } else {
      if (member.getMaritalStatus().equals(Constant.DA_KET_HON)) {
        var generationOfPartner = memberRepository.findById(member.getPartnerId());
        generationOfPartner.ifPresent(value->member.setGeneration(value.getGeneration()));
        // update partner sang kết hôn
        memberRepository.updateMaritalStatus(Constant.DA_KET_HON, generationOfPartner.get().getId());
      } else {
        if (member.getDadId() != null) {
          var generationOfDad = memberRepository.findById(member.getDadId());
          generationOfDad.ifPresent(value->member.setGeneration(value.getGeneration() + 1));
        } else if (member.getMomId() != null) {
          var generationOfMom = memberRepository.findById(member.getMomId());
          generationOfMom.ifPresent(value->member.setGeneration(value.getGeneration() + 1));
        } else {
          throw new FamilyTreeException(
              ExceptionUtils.MOM_OR_DAD_IS_NOT_NULL,
              ExceptionUtils.messages.get(ExceptionUtils.MOM_OR_DAD_IS_NOT_NULL));
        }
      }
      // nếu đã có ông tổ hoặc trưởng họ rồi thì báo lỗi
      if(member.getRole().equals(Constant.TRUONG_HO)){
        var checkExistRole = memberRepository.checkExistRole(Constant.TRUONG_HO);
        if (checkExistRole.isPresent()) {
          throw new FamilyTreeException(
              ExceptionUtils.TRUONG_HO_ALREADY_EXISTS,
              ExceptionUtils.messages.get(ExceptionUtils.TRUONG_HO_ALREADY_EXISTS));
        }
      }
      if(member.getRole().equals(Constant.ONG_TO)){
        var checkExistRole = memberRepository.checkExistRole(Constant.ONG_TO);
        if (checkExistRole.isPresent()) {
          throw new FamilyTreeException(
              ExceptionUtils.ONG_TO_ALREADY_EXISTS,
              ExceptionUtils.messages.get(ExceptionUtils.ONG_TO_ALREADY_EXISTS));
        }
      }
    }
    var userCheck = memberRepository.findByUserName(member.getUserName());
    if (userCheck.isPresent()) {
      throw new FamilyTreeException(
          ExceptionUtils.USER_SIGNUP_1, ExceptionUtils.messages.get(ExceptionUtils.USER_SIGNUP_1));
    }
    // cấp role cho từng vai trò
    if(member.getRole().equals(Constant.TRUONG_HO)){
      member.setCanAdd(true);
      member.setCanEdit(true);
      member.setCanView(true);
    }else {
      member.setCanAdd(false);
      member.setCanEdit(false);
      member.setCanView(true);
    }
    memberRepository.save(member);
  }

  /**
   * Lấy thông tin thành viên theo id
   *
   * @author nga
   */
  @Override
  public Member getMemberById(Long id) throws FamilyTreeException {
    var member = memberRepository.findById(id);
    if (member.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.ID_IS_NOT_EXIST));
    }
    return member.get();
  }

  /**
   * sửa thông tin thành viên
   *
   * @author nga
   */
  @Override
  public void update(Member member) throws FamilyTreeException {
    //check xem thành viên có tồn tại trong gia phả ko
    var memberOptional = this.getMemberById(member.getId());
    //lưu lại thông tin sau khi đã sửa
    memberRepository.save(member);
  }

}
