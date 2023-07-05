package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.Member;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.GenealogicalStatisticsDTO;
import com.example.familytree.models.GenerationDTO;
import com.example.familytree.models.UserDTO;
import com.example.familytree.models.membermanagement.LoginDTO;
import com.example.familytree.models.membermanagement.SignUpReqDTO;
import com.example.familytree.repository.MemberRepository;
import com.example.familytree.repository.PermissionManagementRepository;
import com.example.familytree.service.MemberService;
import io.micrometer.common.util.StringUtils;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
  private final MemberRepository memberRepository;
  private final PermissionManagementRepository permissionManagementRepository;

  /**
   * đăng nhập vào tài khoản
   *
   * @param userName, password
   * @author nga
   */
  @Override
  @Transactional
  public LoginDTO logIn(String userName, String password) throws FamilyTreeException {
    // check đầu vào not null
    if (userName == null || password == null) {
      throw new FamilyTreeException(
          ExceptionUtils.USER_LOGIN_1, ExceptionUtils.messages.get(ExceptionUtils.USER_LOGIN_1));
    }
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
    // lấy thông tin nhóm quyền theo role người đăng nhập
    var permissionManagement =
        permissionManagementRepository.findByPermissionGroupName(member.getRole());
    if (permissionManagement.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.PERMISSION_ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.PERMISSION_ID_IS_NOT_EXIST));
    }
    var permission = permissionManagement.get();
    LoginDTO loginDTO =
        LoginDTO.builder()
            .memberId(member.getId())
            .fullName(member.getFullName())
            .userName(member.getUserName())
            .password(member.getPassword())
            .role(member.getRole())
            .viewMembers(permission.getViewMebers())
            .updateMembers(permission.getUpdateMembers())
            .createMembers(permission.getCreateMembers())
            .viewFinancial(permission.getViewFinancial())
            .createFinancial(permission.getCreateFinancial())
            .updateFinancial(permission.getUpdateFinancial())
            .deleteFinancial(permission.getDeleteFinancial())
            .viewEvent(permission.getViewEvent())
            .createEvent(permission.getCreateEvent())
            .updateEvent(permission.getUpdateEvent())
            .deleteEvent(permission.getDeleteEvent())
            .build();
    return loginDTO;
  }

  /**
   * Đăng ký tài khoản
   *
   * @param reqDTO
   * @throws FamilyTreeException
   * @since 01/07/2023
   */
  @Override
  @Transactional
  public void signUp(SignUpReqDTO reqDTO) throws FamilyTreeException {
    var member = this.getMemberById(reqDTO.getMemberId());
    // check thành viên đã đăng ký tài khoản hay chưa
    // TH thành viên chưa đăng ký
    if(StringUtils.isBlank(member.getUserName())){
      // check username đã tồn tại
      this.findByUserName(reqDTO.getUserName());
      // TH chưa đăng ký
      member.setUserName(reqDTO.getUserName());
      member.setPassword(reqDTO.getPassword());
      member.setRole(reqDTO.getRole());
    } else {
      throw new FamilyTreeException(
          ExceptionUtils.USER_SIGNUP_1, ExceptionUtils.messages.get(ExceptionUtils.USER_SIGNUP_1));
    }
  }

  /**
   * Sửa thông tin tài khoản đã đăng ký
   *
   * @param reqDTO
   * @throws FamilyTreeException
   * @since 03/07/2023
   */
  @Override
  @Transactional
  public void updateAccount(SignUpReqDTO reqDTO) throws FamilyTreeException {
    var member = this.getMemberById(reqDTO.getMemberId());
    // check thành viên đã đăng ký tài khoản hay chưa
    // TH thành viên chưa đăng ký => y/c đăng ký tài khoản trước
    if (StringUtils.isBlank(member.getUserName())) {
      throw new FamilyTreeException(
          ExceptionUtils.ACCOUNT_DOES_NOT_HAVE,
          ExceptionUtils.messages.get(ExceptionUtils.ACCOUNT_DOES_NOT_HAVE));
    }
    this.findByUserName(reqDTO.getUserName());
    member.setUserName(reqDTO.getUserName());
    member.setPassword(reqDTO.getPassword());
    member.setRole(reqDTO.getRole());
  }

  /**
   * Validate check userName đã tồn tại
   *
   * @param userName
   * @throws FamilyTreeException
   */
  private void findByUserName(String userName) throws FamilyTreeException {
    var member = memberRepository.findByUserName(userName);
    // TH username đã tồn tại
    if (member.isPresent()) {
      throw new FamilyTreeException(
          ExceptionUtils.USER_ALREADY_EXISTS,
          ExceptionUtils.messages.get(ExceptionUtils.USER_ALREADY_EXISTS));
    }
  }

  /**
   * Xóa 1 tài khoản đã đăng ký theo id của thành viên
   *
   * @param userName
   * @throws FamilyTreeException
   * @since 03/07/2023
   */
  @Override
  @Transactional
  public void deleteAccount(String userName) throws FamilyTreeException {
    var memberOptional = memberRepository.findByUserName(userName);
    if (memberOptional.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.USER_LOGIN_2, ExceptionUtils.messages.get(ExceptionUtils.USER_LOGIN_2));
    }
    var member = memberOptional.get();
    member.setUserName(null);
    member.setPassword(null);
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
      this.checkRoleIsExist(member);
    }

    if (member.getMaritalStatus().equals(Constant.DA_KET_HON)) {
      Member partner = this.getMemberById(member.getPartnerId());
      // Check xem đối tượng đã có vợ/chồng hay chưa :v
      if (StringUtils.isBlank(String.valueOf(partner.getPartnerId()))
          || partner.getMaritalStatus().equals(Constant.DA_KET_HON)) {
        throw new FamilyTreeException(ExceptionUtils.MEMBER_HAD_A_WIFE_OR_HUSBAND, ExceptionUtils.messages.get(ExceptionUtils.MEMBER_HAD_A_WIFE_OR_HUSBAND));
      }
      var generation = partner.getGeneration();
      member.setGeneration(generation);
      // update partner sang đã kết hôn
      var partnerId = memberRepository.findMaxSeq();
      partner.setMaritalStatus(Constant.DA_KET_HON);
      partner.setPartnerId(partnerId);
    } else {
      this.setGenerationByDadIdOrMomId(member);
    }
    memberRepository.save(member);
  }

  /**
   * Hàm check điều kiện role chỉ có 1 trưởng họ và 1 ông tổ
   *
   * @param member
   * @throws FamilyTreeException
   */
  private void checkRoleIsExist(Member member) throws FamilyTreeException {
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

  /**
   * validate tách hàm thiết lập đời khi thêm thành viên
   * 
   * @param member
   * @throws FamilyTreeException
   */
  private void setGenerationByDadIdOrMomId(Member member) throws FamilyTreeException {
    if (member.getDadId() != null) {
      var generationOfDad = memberRepository.findById(member.getDadId());
      generationOfDad.ifPresent(value-> member.setGeneration(value.getGeneration() + 1));
    } else if (member.getMomId() != null) {
      var generationOfMom = memberRepository.findById(member.getMomId());
      generationOfMom.ifPresent(value-> member.setGeneration(value.getGeneration() + 1));
    } else {
      throw new FamilyTreeException(
          ExceptionUtils.MOM_OR_DAD_IS_NOT_NULL,
          ExceptionUtils.messages.get(ExceptionUtils.MOM_OR_DAD_IS_NOT_NULL));
    }
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
          ExceptionUtils.MEMBER_ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.MEMBER_ID_IS_NOT_EXIST));
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
    checkRoleIsExist(member);
    //check TH chuyển trạng thái tình trạng hôn nhân sang Độc thân
    if(member.getMaritalStatus().equals(Constant.DOC_THAN)){
      member.setMaritalStatus(Constant.DOC_THAN);
      member.setPartnerId(null);
    }
    // check TH chuyển trạng thái từ độc thân sang đã kết hôn
    if(member.getMaritalStatus().equals(Constant.DA_KET_HON)){
      var partner = this.getMemberById(member.getPartnerId());
      partner.setMaritalStatus(Constant.DA_KET_HON);
      partner.setPartnerId(member.getId());
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
    // tìm số thành viên có tuổi từ 18 đến 60 và status <> đã mất
    LocalDate today = LocalDate.now();
    var endDate = today.minusYears(18);
    var startDate = today.minusYears(60);
    List<Member> membersByAge =
        memberRepository.findAllAgeInTheRange(endDate, startDate, Constant.DA_MAT);
    return membersByAge;
  }

  /**
   * Tìm tất cả những thành viên có tài khoản đăng nhập vào phần mềm
   *
   * @return
   * @throws FamilyTreeException
   * @since 01/07/2023
   */
  @Override
  public List<UserDTO> findAllAccounts() throws FamilyTreeException {
    List<Member> memberList = memberRepository.findAllByUserName();
    List<UserDTO> userDTOList =
        memberList.stream()
            .map(
                member ->
                    new UserDTO(
                        member.getId(),
                        member.getFullName(),
                        member.getUserName(),
                        member.getPassword(),
                        member.getRole()))
            .toList();
    return userDTOList;
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
    List<Member> numberOfFemale =
        memberRepository.findAllByDateOfDeathOrStatusAndGender(Constant.DA_MAT, gender);
    int totalAge = 0;
    if(numberOfFemale.isEmpty()){
      return totalAge;
    }
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
    if(members.isEmpty()){
      return count;
    }
    for (Member member : members) {
      if (member.getGeneration() == genderation) {
        count++;
      }
    }
    return count;
  }
}
