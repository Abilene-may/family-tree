package com.example.familytree.service;

import com.example.familytree.domain.RevenueManagement;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.RevenueManagementDIS;
import com.example.familytree.models.RevenueReport;
import java.time.LocalDate;
import java.util.List;

public interface RevenueManagementService {
  List<RevenueManagement> create() throws FamilyTreeException;
  void update(RevenueManagementDIS revenueManagementDIS) throws FamilyTreeException;
  List<RevenueManagement> getAllRevenue() throws FamilyTreeException;
  RevenueManagement getById(Long id) throws FamilyTreeException;
  RevenueReport report(LocalDate effectiveStartDate, LocalDate effectiveEndDate) throws FamilyTreeException;
  void delete(Long id) throws FamilyTreeException;
}
