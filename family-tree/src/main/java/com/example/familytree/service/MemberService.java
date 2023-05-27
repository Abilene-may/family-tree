package com.example.familytree.service;

import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.GenealogicalStatisticsDTO;
import java.util.List;

public interface MemberService {
  Member logIn(String userName, String password) throws FamilyTreeException;
  List<Member> getAllMember();
  void createMember(Member member)throws FamilyTreeException;
  Member getMemberById(Long id) throws FamilyTreeException;
  void update(Member member) throws FamilyTreeException;
  List<Member> searchMemberByName(String name) throws FamilyTreeException;
   String deAccent(String str);
   GenealogicalStatisticsDTO genealogicalStatisticsDTO() throws FamilyTreeException;

}
