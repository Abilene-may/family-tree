package com.example.familytree.service.impl;

import com.example.familytree.domain.PermissionManagement;
import com.example.familytree.exceptions.ExceptionUtils;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.repository.PermissionManagementRepository;
import com.example.familytree.service.PermissionManagementService;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/** Service xử lý logic quản lý phân quyền */
@Service
@Slf4j
public class PermissionManagementServiceImpl implements PermissionManagementService {
  private final PermissionManagementRepository permissionManagementRepository;

  public PermissionManagementServiceImpl(
      @Lazy PermissionManagementRepository permissionManagementRepository) {
    super();
    this.permissionManagementRepository = permissionManagementRepository;
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
    permissionManagementRepository.save(permissionManagement);
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
  public void update(PermissionManagement permissionManagement) throws FamilyTreeException {
    permissionManagementRepository.save(permissionManagement);
  }

  @Override
  public List<PermissionManagement> getAll() throws FamilyTreeException {
    var permissionManagements = permissionManagementRepository.findAll();
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
   * Xóa một nhóm quyền theo id của nhóm quyền
   *
   * @param id của nhóm quyền
   * @author nga
   * @since 21/06/2023
   */
  @Override
  public void delete(Long id) throws FamilyTreeException {
    var permissionManagement = this.getById(id);
    permissionManagementRepository.delete(permissionManagement);
  }
}
