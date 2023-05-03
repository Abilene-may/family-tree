package com.example.familytree.service;

import com.example.familytree.domain.Member;import com.example.familytree.exceptions.FamilyTreeException;import com.example.familytree.models.MemberDTO;import java.util.List;
public interface MemberService {
  List<Member> getAllMember();
  void createMember(MemberDTO memberDTO)throws FamilyTreeException;
}
