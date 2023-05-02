package com.example.familytree.service.impl;

import com.example.familytree.domain.Member;
import com.example.familytree.repository.MemberRepository;
import com.example.familytree.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;
  @Override
  public List<Member> getAllMember() {
    List<Member> members = memberRepository.findAll();
    return members;
  }
}
