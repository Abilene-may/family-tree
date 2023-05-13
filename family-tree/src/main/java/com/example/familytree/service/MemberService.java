package com.example.familytree.service;

import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.MemberDTO;
import java.util.List;

public interface MemberService {
  Member logIn(String userName, String password) throws FamilyTreeException;
  MemberDTO getAllMember();
  void createMember(Member member)throws FamilyTreeException;
  Member getMemberById(Long id) throws FamilyTreeException;

  void update(Member member) throws FamilyTreeException;
}
