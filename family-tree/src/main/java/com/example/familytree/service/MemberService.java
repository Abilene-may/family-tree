package com.example.familytree.service;

import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.GenealogicalStatisticsDTO;
import com.example.familytree.models.UserDTO;
import com.example.familytree.models.membermanagement.LoginDTO;
import com.example.familytree.models.membermanagement.SignUpReqDTO;
import java.util.List;

public interface MemberService {
  LoginDTO logIn(String userName, String password) throws FamilyTreeException;
  void signUp(SignUpReqDTO reqDTO) throws FamilyTreeException;
  List<Member> getAllMember();
  void createMember(Member member)throws FamilyTreeException;
  Member getMemberById(Long id) throws FamilyTreeException;
  void update(Member member) throws FamilyTreeException;
  List<Member> searchMemberByName(String name) throws FamilyTreeException;
   GenealogicalStatisticsDTO genealogicalStatisticsDTO() throws FamilyTreeException;
   List<Member> findAllAgeInTheRange() throws FamilyTreeException;
   List<UserDTO> findAllAccounts() throws FamilyTreeException;

}
