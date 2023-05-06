package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.MemberDTO;
import com.example.familytree.repository.MemberRepository;
import com.example.familytree.repository.UserRepository;import com.example.familytree.service.MemberService;
import com.example.familytree.service.UserService;import io.micrometer.common.util.StringUtils;import java.util.List;
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
  private final UserRepository userRepository;
  private final UserService userService;

  /**
   * Lấy ra danh sách các thành viên trong gia phả
   *
   * @author nga
   */
  @Override
  public MemberDTO getAllMember() {
    MemberDTO memberDTO = new MemberDTO();
    List<Member> members = memberRepository.findAll();
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
    MemberDTO memberDTO = getAllMember();
    // set đời cho thành viên
    if (memberDTO.getCount() == 0) {
      member.setGeneration(1);
    } else {
      if (member.getMaritalStatus().equals(Constant.DA_KET_HON)) {
        Optional<Member> generationOfPartner = memberRepository.findById(member.getPartnerId());
        generationOfPartner.ifPresent(value->member.setGeneration(value.getGeneration()));
      } else {
        if (member.getDadId() != null) {
          Optional<Member> generationOfDad = memberRepository.findById(member.getDadId());
          generationOfDad.ifPresent(value->member.setGeneration(value.getGeneration() + 1));
        } else if (member.getMomId() != null) {
          Optional<Member> generationOfMom = memberRepository.findById(member.getMomId());
          generationOfMom.ifPresent(value->member.setGeneration(value.getGeneration() + 1));
        } else {
          throw new FamilyTreeException(
              ExceptionUtils.MOM_OR_DAD_IS_NOT_NULL,
              ExceptionUtils.messages.get(ExceptionUtils.MOM_OR_DAD_IS_NOT_NULL));
        }
      }
    }
    userService.signUp(member.getUserName(), member.getPassword(), member.getRole());
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
    Optional<Member> member = memberRepository.findById(id);
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
    Member memberOptional = getMemberById(member.getId());
    //lưu lại thông tin sau khi đã sửa
    memberRepository.save(member);
  }

}
