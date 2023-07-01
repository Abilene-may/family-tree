package com.example.familytree.service;

import com.example.familytree.domain.RevenueDetail;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.RevenueDetailDTO;
import java.time.LocalDate;
import java.util.List;

public interface RevenueDetailService {
  List<RevenueDetail> getAllByIdRevenueManagement(Long idRevenueManagemnet, LocalDate startDate)
      throws FamilyTreeException;

  void update(RevenueDetailDTO revenueDetailDTO) throws FamilyTreeException;
  RevenueDetail getById(Long id) throws FamilyTreeException;
}
