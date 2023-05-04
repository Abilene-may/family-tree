package com.example.familytree.service;

import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.FamilyTreeException;
import java.util.List;

public interface MemberService {
  List<Member> getAllMember();
  void createMember(Member member)throws FamilyTreeException;
  Member getMemberById(Long id) throws FamilyTreeException;
}
