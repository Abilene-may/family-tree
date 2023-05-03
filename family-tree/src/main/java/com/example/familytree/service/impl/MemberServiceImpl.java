package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.FamilyTreeException;import com.example.familytree.models.MemberDTO;import com.example.familytree.repository.MemberRepository;
import com.example.familytree.service.MemberService;
import com.example.familytree.service.UserService;import java.util.List;
import lombok.Builder;import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Builder
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;
  private final UserService userService;

  /**
   * Lấy ra danh sách các thành viên trong gia phả
   *
   * @author nga
   */
  @Override
  public List<Member> getAllMember() {
    List<Member> members = memberRepository.findAll();
    return members;
  }

  /**
   * thêm thành viên mới đồng thời cấp user
   *
   * @author nga
   */
  @Override
  public void createMember(MemberDTO memberDTO)throws FamilyTreeException {
    Member member =
        Member.builder()
            .fullName(memberDTO.getFullName())
            .gender(memberDTO.getGender())
            .dateOfBirth(memberDTO.getDateOfBirth())
            .mobilePhoneNumber(memberDTO.getMobilePhoneNumber())
            .career(memberDTO.getCareer())
            .education(memberDTO.getEducation())
            .nameDad(memberDTO.getNameDad())
            .nameMom(memberDTO.getNameMom())
            .maritalStatus(memberDTO.getMaritalStatus())
            .namePartner(memberDTO.getNamePartner())
            .role(memberDTO.getRole())
            .status(memberDTO.getStatus())
            .dateOfDeath(memberDTO.getDateOfDeath())
            .burialPlace(memberDTO.getBurialPlace())
            .build();
    if (memberDTO.getRole().equals(Constant.TRUONG_HO)) {
      member.setCanEdit(true);
      member.setCanAdd(true);
      member.setCanView(true);
    } else {
      member.setCanEdit(false);
      member.setCanAdd(false);
      member.setCanView(true);
    }
    memberRepository.save(member);
    userService.signUp(memberDTO.getUserName(), memberDTO.getPassword(), memberDTO.getRole());
  }
}
