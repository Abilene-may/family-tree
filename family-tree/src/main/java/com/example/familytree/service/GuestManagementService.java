package com.example.familytree.service;

import com.example.familytree.domain.GuestManagement;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.guestmanagement.GuestManagementReqDTO;
import java.util.List;

public interface GuestManagementService {
  // Thiết lập khách theo thông tin đầu vào
  List<GuestManagement> setUpListGuest(GuestManagementReqDTO guestManagementReqDTO)
      throws FamilyTreeException;

  void delete(Long id) throws FamilyTreeException;
  void deleteAll(Long eventManagementId) throws FamilyTreeException;

  List<GuestManagement> getAll() throws FamilyTreeException;
  List<GuestManagement> findAllByEventManagementId(Long eventManagementId) throws FamilyTreeException;
}
