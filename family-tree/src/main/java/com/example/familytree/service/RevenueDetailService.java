package com.example.familytree.service;

import com.example.familytree.domain.RevenueDetail;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.RevenueDetailDTO;
import java.util.List;

public interface RevenueDetailService {
  List<RevenueDetail> getAllByIdRevenueManagement(Long idRevenueManagemnet) throws FamilyTreeException;
  void update(RevenueDetailDTO revenueDetailDTO) throws FamilyTreeException;
}
