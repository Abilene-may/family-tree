package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.GenealogicalStatisticsDTO;
import com.example.familytree.models.GenerationDTO;
import com.example.familytree.repository.MemberRepository;
import com.example.familytree.service.MemberService;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
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
  public List<Member> getAllMember() {
    var members = memberRepository.findAll();
    return members;
  }

  /**
   * thêm thành viên mới vào gia phả
   *
   * @author nga
   * @since 03/05/2023
   */
  @Override
  @Transactional
  public void createMember(Member member) throws FamilyTreeException {
    var memberList = this.getAllMember();
    // set đời cho thành viên
    // TH tạo ông tổ
    if (memberList.size() == 0) {
      member.setGeneration(1);
    } else {
      if (member.getMaritalStatus().equals(Constant.DA_KET_HON)) {
        var generationOfPartner = memberRepository.findById(member.getPartnerId());
        if(generationOfPartner.isPresent()){
          var generation = generationOfPartner.get().getGeneration();
          member.setGeneration(generation);
          // update partner sang kết hôn
          memberRepository.updateMaritalStatus(
              Constant.DA_KET_HON, generationOfPartner.get().getId());
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
      if(member.getRole().equals(Constant.TRUONG_HO)){
        var checkExistRole = memberRepository.checkExistRole(Constant.TRUONG_HO);
        if (checkExistRole.isPresent()) {
          throw new FamilyTreeException(
              ExceptionUtils.TRUONG_HO_ALREADY_EXISTS,
              ExceptionUtils.messages.get(ExceptionUtils.TRUONG_HO_ALREADY_EXISTS));
        }
      } else if(member.getRole().equals(Constant.ONG_TO)){
        var checkExistRole = memberRepository.checkExistRole(Constant.ONG_TO);
        if (checkExistRole.isPresent()) {
          throw new FamilyTreeException(
              ExceptionUtils.ONG_TO_ALREADY_EXISTS,
              ExceptionUtils.messages.get(ExceptionUtils.ONG_TO_ALREADY_EXISTS));
        }
      }
    }
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
   * sửa thông tin thành viên
   *
   * @author nga
   */
  @Override
  public void update(Member member) throws FamilyTreeException {
    //check xem thành viên có tồn tại trong gia phả ko
    var memberById = this.getMemberById(member.getId());
    //check TH chuyển role
    // nếu đã có ông tổ hoặc trưởng họ rồi thì báo lỗi
    if(member.getRole().equals(Constant.TRUONG_HO)){
      var checkExistRole = memberRepository.checkExistRole(Constant.TRUONG_HO);
      if (checkExistRole.isPresent()) {
        throw new FamilyTreeException(
            ExceptionUtils.TRUONG_HO_ALREADY_EXISTS,
            ExceptionUtils.messages.get(ExceptionUtils.TRUONG_HO_ALREADY_EXISTS));
      }
    } else if(member.getRole().equals(Constant.ONG_TO)){
      var checkExistRole = memberRepository.checkExistRole(Constant.ONG_TO);
      if (checkExistRole.isPresent()) {
        throw new FamilyTreeException(
            ExceptionUtils.ONG_TO_ALREADY_EXISTS,
            ExceptionUtils.messages.get(ExceptionUtils.ONG_TO_ALREADY_EXISTS));
      }
    }
    //check TH chuyển trạng thái tình trạng hôn nhân sang Độc thân
    if(member.getMaritalStatus().equals(Constant.DOC_THAN)){
      member.setMaritalStatus(Constant.DOC_THAN);
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
    List<Member> members = memberRepository.findAllByFullName(fullName);
    return members;
  }


  /**
   * tab màn thống kê
   *
   * @author nga
   * @since 27/05/2023
   */
  @Override
  public GenealogicalStatisticsDTO genealogicalStatisticsDTO() throws FamilyTreeException {
    GenealogicalStatisticsDTO genealogicalStatisticsDTO = new GenealogicalStatisticsDTO();
    // Tính tổng số nam, nữ trong gia phả
    var totalOfMale = this.getSumByGender(Constant.NAM);
    var totalOfFemale = this.getSumByGender(Constant.NU);
    genealogicalStatisticsDTO.setNumberOfMale(totalOfMale);
    genealogicalStatisticsDTO.setNumberOfFemale(totalOfFemale);
    // tìm số thành viên có tuổi từ 18 đến 60
    var memberList = this.findAllAgeInTheRange();
    genealogicalStatisticsDTO.setAgeInTheRange(memberList.size());

    // tổng số thành viên trong gia phả
    var total = totalOfFemale + totalOfMale;
    genealogicalStatisticsDTO.setTotalMember(total);
    // Tính tuổi thọ TB của nữ
    var averageAgeOfFemale = averageLifespan(Constant.NU);
    // Tính tuổi thọ TB của nam
    var averageAgeOfMale = averageLifespan(Constant.NAM);
    genealogicalStatisticsDTO.setAverageAgeOfFemale(averageAgeOfFemale);
    genealogicalStatisticsDTO.setAverageAgeOfMale(averageAgeOfMale);

    // tìm tất cả các đời có trong gia phả
    List<Integer> genderation = memberRepository.findAllByGenderation();
    var members = this.getAllMember();
    // Thống kê đời trong gia phả
    List<GenerationDTO> generationDTOS = new ArrayList<>();
    for (int i = 0; i < genderation.size(); i++) {
      var count = this.numberOfGeneration(members, genderation.get(i));
      generationDTOS.add(new GenerationDTO(genderation.get(i), count));
    }
    genealogicalStatisticsDTO.setGenerationDTOS(generationDTOS);
    return genealogicalStatisticsDTO;
  }

  /*
   * tìm list thành viên có tuổi từ 18 đến 60
   */
  @Override
  public List<Member> findAllAgeInTheRange() throws FamilyTreeException {
    // tìm số thành viên có tuổi từ 18 đến 60
    LocalDate today = LocalDate.now();
    var endDate = today.minusYears(18);
    var startDate = today.minusYears(60);
    List<Member> membersByAge = memberRepository.findAllAgeInTheRange(endDate, startDate);
    return membersByAge;
  }

  /* Tính tổng số nam hoặc nữ */
  public int getSumByGender(String gender){
    // tìm tổng số thành viên theo giới tính
    var members = memberRepository.findAllByGender(gender);
    return members.size();
  }

  /*
   * Tính tuổi thọ trung bình trong gia phả
   */
  public int averageLifespan(String gender) {
    // Tìm số thành viên đã mất với giới tính gender
    List<Member> numberOfFemale = memberRepository.findAllByDateOfDeathAndGender(gender);
    int totalAge = 0;
    int memberCount = numberOfFemale.size();
    // Tính tuổi thọ TB
    for (int i = 0; i < memberCount; i++) {
      LocalDate deathDate = numberOfFemale.get(i).getDateOfDeath();
      LocalDate birthDate = numberOfFemale.get(i).getDateOfBirth();
      int age = Period.between(birthDate, deathDate).getYears();
      totalAge += age;
    }
    var averageLifespan = totalAge / memberCount;
    return averageLifespan;
  }

  /*
   * Hàm tính số thành viên của thế hệ
   */
  public int numberOfGeneration(List<Member> members, int genderation) {
    int count = 0;
    for (Member member : members) {
      if (member.getGeneration() == genderation) {
        count++;
      }
    }
    return count;
  }
}
