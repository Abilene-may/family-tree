package com.example.familytree.service.impl;

import com.example.familytree.commons.Constant;
import com.example.familytree.domain.Member;
import com.example.familytree.domain.PermissionManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.repository.MemberRepository;
import com.example.familytree.repository.PermissionManagementRepository;
import com.example.familytree.service.PermissionManagementService;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service xử lý logic quản lý phân quyền */
@Service
@Slf4j
public class PermissionManagementServiceImpl implements PermissionManagementService {
  private final PermissionManagementRepository permissionManagementRepository;
  private final MemberRepository memberRepository;

  public PermissionManagementServiceImpl(
      @Lazy PermissionManagementRepository permissionManagementRepository,
      @Lazy MemberRepository memberRepository) {
    super();
    this.permissionManagementRepository = permissionManagementRepository;
    this.memberRepository = memberRepository;
  }

  /**
   * Tạo một nhóm quyền mới
   *
   * @param permissionManagement
   * @return
   * @author nga
   * @since 21/06/2023
   */
  @Override
  public PermissionManagement create(PermissionManagement permissionManagement)
      throws FamilyTreeException {
    // check tên nhóm quyền không được null
    if (StringUtils.isBlank(permissionManagement.getPermissionGroupName())) {
      throw new FamilyTreeException(
          ExceptionUtils.PERMISSION_MANAGEMENT_IS_NOT_BLANK,
          ExceptionUtils.messages.get(ExceptionUtils.PERMISSION_MANAGEMENT_IS_NOT_BLANK));
    }
    // check tên nhóm quyền đã tồn tại trong gia phả
    var checkRoleExits =
        permissionManagementRepository.findByPermissionGroupName(
            permissionManagement.getPermissionGroupName());
    permissionManagementRepository.save(permissionManagement);
    if (checkRoleExits.isPresent()) {
      throw new FamilyTreeException(
          ExceptionUtils.NAME_PERMISSION_ALREADY_EXISTS,
          ExceptionUtils.messages.get(ExceptionUtils.NAME_PERMISSION_ALREADY_EXISTS));
    }
    return permissionManagement;
  }

  /**
   * Sửa thông tin một nhóm quyền
   *
   * @param permissionManagement
   * @author nga
   * @since 21/06/2023
   */
  @Override
  @Transactional
  public void update(PermissionManagement permissionManagement) throws FamilyTreeException {
    var checkPermission = this.getById(permissionManagement.getId());
    var roleNew = permissionManagement.getPermissionGroupName();
    // Tìm DS các thành viên thuộc nhóm quyền cũ
    var memberList = memberRepository.findAllByRole(checkPermission.getPermissionGroupName());
    for (Member m : memberList) {
        m.setRole(roleNew);
    }
    permissionManagementRepository.save(permissionManagement);
  }

  @Override
  public List<PermissionManagement> getAll() throws FamilyTreeException {
    var permissionManagements = permissionManagementRepository.findAll();
    // thiết lập những nhóm quyền sẵn trong gia phả
    if(permissionManagements.isEmpty()){
      PermissionManagement adminPermission =
          PermissionManagement.builder()
              .permissionGroupName(Constant.TRUONG_HO)
              .permissionsDescription(Constant.TRUONG_HO_DESCRIPTION)
              .viewMebers(true)
              .updateMembers(true)
              .createMembers(true)
              .viewFinancial(true)
              .createFinancial(true)
              .updateFinancial(true)
              .deleteFinancial(true)
              .viewEvent(true)
              .createEvent(true)
              .updateEvent(true)
              .deleteEvent(true)
              .build();
      PermissionManagement memberPermission =
          PermissionManagement.builder()
              .permissionGroupName(Constant.THANH_VIEN)
              .permissionsDescription(Constant.THANH_VIEN_DESCRIPTION)
              .viewMebers(true)
              .updateMembers(false)
              .createMembers(false)
              .viewFinancial(true)
              .createFinancial(false)
              .updateFinancial(false)
              .deleteFinancial(false)
              .viewEvent(true)
              .createEvent(false)
              .updateEvent(false)
              .deleteEvent(false)
              .build();
      permissionManagements.add(adminPermission);
      permissionManagements.add(memberPermission);
      permissionManagementRepository.saveAll(permissionManagements);
    }
    return permissionManagements;
  }

  /**
   * Xem thông tin chi tiết một quyền
   *
   * @param id của nhóm quyền
   * @author nga
   * @since 21/06/2023
   */
  @Override
  public PermissionManagement getById(Long id) throws FamilyTreeException {
    var optional = permissionManagementRepository.findById(id);
    if (optional.isEmpty()) {
      throw new FamilyTreeException(
          ExceptionUtils.PERMISSION_ID_IS_NOT_EXIST,
          ExceptionUtils.messages.get(ExceptionUtils.PERMISSION_ID_IS_NOT_EXIST));
    }
    return optional.get();
  }

  /**
   * Xóa một nhóm quyền đông thời thay thế bằng một nhóm quyền khác
   *
   * @param id của nhóm quyền, idPermissionOther của nhóm quyền thay thế
   * @author nga
   * @since 21/06/2023
   */
  @Override
  @Transactional
  public void delete(Long id, Long idPermissionReplace) throws FamilyTreeException {
    var permissionManagement = this.getById(id);
    // check thông tin của nhóm quyền chọn thay thế
    var permissionManagementReplace = this.getById(idPermissionReplace);
    var role = permissionManagement.getPermissionGroupName();
    var roleReplace = permissionManagementReplace.getPermissionGroupName();
    // Tìm tất cả thành viên có nhóm quyền cũ cần xóa
    List<Member> memberList = memberRepository.findAllByRole(role);
    // sửa lại thành nhóm quyền thay thế
    for (Member m : memberList ) {
        m.setRole(roleReplace);
    }
    permissionManagementRepository.delete(permissionManagement);
  }
}
