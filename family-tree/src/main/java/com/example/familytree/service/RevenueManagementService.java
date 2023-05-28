package com.example.familytree.service;

import com.example.familytree.domain.RevenueManagement;
import com.example.familytree.exceptions.FamilyTreeException;
import java.util.List;

public interface RevenueManagementService {
  RevenueManagement create(RevenueManagement revenueManagement) throws FamilyTreeException;
  void update(RevenueManagement revenueManagement) throws FamilyTreeException;
  List<RevenueManagement> getAllRevenue() throws FamilyTreeException;
  RevenueManagement getById(Long id) throws FamilyTreeException;

}
