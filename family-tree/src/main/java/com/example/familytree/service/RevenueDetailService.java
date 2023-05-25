package com.example.familytree.service;

import com.example.familytree.domain.RevenueDetail;
import com.example.familytree.domain.RevenueManagement;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.RevenueReport;
import java.util.List;

public interface RevenueDetailService {
  RevenueDetail create(RevenueDetail revenueDetail) throws FamilyTreeException;
  List<RevenueDetail> getAll() throws FamilyTreeException;
  RevenueDetail getById(Long id) throws FamilyTreeException;
  void update(RevenueDetail revenueDetail) throws FamilyTreeException;
  void delete(Long id) throws FamilyTreeException;
  RevenueReport report(Integer year, String typeOfRevenue) throws FamilyTreeException;
}
