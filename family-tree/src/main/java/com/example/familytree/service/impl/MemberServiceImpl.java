package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.Member;
import com.example.familytree.domain.User;import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.MemberDTO;import com.example.familytree.repository.MemberRepository;
import com.example.familytree.repository.UserRepository;import com.example.familytree.service.MemberService;
import com.example.familytree.service.UserService;import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
  public void createMember(Member member)throws FamilyTreeException {
    List<Member> members = getAllMember();
    if (members.isEmpty()){
      member.setGeneration(1);
    }
    userService.signUp(member.getUserName(), member.getPassword(), member.getRole());
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
}
