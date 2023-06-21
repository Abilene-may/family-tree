package com.example.familytree.service;

import com.example.familytree.domain.ExpenseManagement;
import com.example.familytree.domain.PermissionManagement;
import com.example.familytree.exceptions.FamilyTreeException;
import java.util.List;

public interface PermissionManagementService {
  PermissionManagement create(PermissionManagement permissionManagement) throws FamilyTreeException;
  void update(PermissionManagement permissionManagement) throws FamilyTreeException;
  List<PermissionManagement> getAll() throws FamilyTreeException;
  PermissionManagement getById(Long id) throws FamilyTreeException;
  void delete(Long id) throws FamilyTreeException;
}
