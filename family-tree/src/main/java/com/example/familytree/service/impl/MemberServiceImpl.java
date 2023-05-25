package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.MemberDTO;
import com.example.familytree.repository.MemberRepository;
import com.example.familytree.service.MemberService;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
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
  @Transactional
  public Member logIn(String userName, String password) throws FamilyTreeException {
    if (userName == null || password == null) {
      throw new FamilyTreeException(
          ExceptionUtils.USER_LOGIN_1, ExceptionUtils.messages.get(ExceptionUtils.USER_LOGIN_1));
    } else {
      var memberOptional = memberRepository.findByUserName(userName);
      if (memberOptional.isEmpty()) {
        throw new FamilyTreeException(
            ExceptionUtils.USER_LOGIN_2, ExceptionUtils.messages.get(ExceptionUtils.USER_LOGIN_2));
      }
      var member = memberOptional.get();
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
   * @since 03/05/2023
   */
  @Override
  @Transactional
  public void createMember(Member member) throws FamilyTreeException {
    // convert về dạng tiếng việt không dấu
    var maritalSearch = this.deAccent(member.getMaritalStatus());
    var roleSearch = this.deAccent(member.getRole());
    var memberDTO = this.getAllMember();
    // set đời cho thành viên
    // TH tạo ông tổ
    if (memberDTO.getCount() == 0) {
      member.setGeneration(1);
    } else {
      if (maritalSearch.equals(Constant.DA_KET_HON)) {
        var generationOfPartner = memberRepository.findById(member.getPartnerId());
        if(generationOfPartner.isPresent()){
          var generation = generationOfPartner.get().getGeneration();
          member.setGeneration(generation);
          // update partner sang kết hôn
          memberRepository.updateMaritalStatus(
              Constant.DA_KET_HON_TV, Constant.DA_KET_HON, generationOfPartner.get().getId());
        }

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
      if(roleSearch.equals(Constant.TRUONG_HO)){
        var checkExistRole = memberRepository.checkExistRole(Constant.TRUONG_HO);
        if (checkExistRole.isPresent()) {
          throw new FamilyTreeException(
              ExceptionUtils.TRUONG_HO_ALREADY_EXISTS,
              ExceptionUtils.messages.get(ExceptionUtils.TRUONG_HO_ALREADY_EXISTS));
        }
        // cấp role cho trưởng họ
        member.setCanAdd(true);
        member.setCanEdit(true);
        member.setCanView(true);
      } else if(roleSearch.equals(Constant.ONG_TO)){
        var checkExistRole = memberRepository.checkExistRole(Constant.ONG_TO);
        if (checkExistRole.isPresent()) {
          throw new FamilyTreeException(
              ExceptionUtils.ONG_TO_ALREADY_EXISTS,
              ExceptionUtils.messages.get(ExceptionUtils.ONG_TO_ALREADY_EXISTS));
        }
        member.setCanAdd(false);
        member.setCanEdit(false);
        member.setCanView(true);
      } else {
        member.setCanAdd(false);
        member.setCanEdit(false);
        member.setCanView(true);
      }
    }
    member.setNameSearch(this.deAccent(member.getFullName()));
    member.setMaritalSearch(maritalSearch);
    member.setRoleSearch(roleSearch);
    // kiểm tra username đã tồn tại hay chưa (username không được trùng)
    var userCheck = memberRepository.findByUserName(member.getUserName());
    if (userCheck.isPresent()) {
      throw new FamilyTreeException(
          ExceptionUtils.USER_SIGNUP_1, ExceptionUtils.messages.get(ExceptionUtils.USER_SIGNUP_1));
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
   * update imformation member
   *
   * @author nga
   */
  @Override
  public void update(Member member) throws FamilyTreeException {
    //check xem thành viên có tồn tại trong gia phả ko
    var memberById = this.getMemberById(member.getId());
    // convert về dạng tiếng việt không dấu
    var maritalSearch = deAccent(member.getMaritalStatus());
    var roleSearch = deAccent(member.getRole());
    //check TH chuyển role
    // nếu đã có ông tổ hoặc trưởng họ rồi thì báo lỗi
    if(roleSearch.equals(Constant.TRUONG_HO)){
      var checkExistRole = memberRepository.checkExistRole(Constant.TRUONG_HO);
      if (checkExistRole.isPresent()) {
        throw new FamilyTreeException(
            ExceptionUtils.TRUONG_HO_ALREADY_EXISTS,
            ExceptionUtils.messages.get(ExceptionUtils.TRUONG_HO_ALREADY_EXISTS));
      }
      //cấp lại role cho trưởng họ
      member.setCanAdd(true);
      member.setCanEdit(true);
    } else if(roleSearch.equals(Constant.ONG_TO)){
      var checkExistRole = memberRepository.checkExistRole(Constant.ONG_TO);
      if (checkExistRole.isPresent()) {
        throw new FamilyTreeException(
            ExceptionUtils.ONG_TO_ALREADY_EXISTS,
            ExceptionUtils.messages.get(ExceptionUtils.ONG_TO_ALREADY_EXISTS));
      }
      member.setCanAdd(false);
      member.setCanEdit(false);
    }  else {
      member.setCanAdd(false);
      member.setCanEdit(false);
    }
    //check TH chuyển trạng thái tình trạng hôn nhân sang Độc thân
    if(maritalSearch.equals(Constant.DOC_THAN)){
      member.setMaritalSearch(maritalSearch);
      member.setPartnerId(null);
    }
    //lưu lại thông tin sau khi đã sửa
    memberRepository.save(member);
  }
  /**
   * Tìm kiếm thành viên theo tên
   *
   * @author nga
   * @since 17/05/2023
   */
  @Override
  public List<Member> searchMemberByName(String fullName) throws FamilyTreeException {
    var deAccent = this.deAccent(fullName);
    List<Member> members = memberRepository.findAllByFullName(deAccent);
    return members;
  }

  /**
  * Convert dữ liệu về dạng tiếng việt không dấu
   * @param str
   * @return chuỗi thông tin đầu vào không dấu
   * @author nga
   * @Since 18/05
  */
  @Override
  public String deAccent(String str) {
    if (str.isBlank()) {
      return "";
    }
    str = str.toLowerCase(Locale.ROOT);
    str = str.replace("đ", "d");
    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    str = pattern.matcher(nfdNormalizedString).replaceAll("");
    str = str.replaceAll("[^\\w\\s]", " ");
    str = str.replaceAll("\\s", " ");
    return str;
  }
}
