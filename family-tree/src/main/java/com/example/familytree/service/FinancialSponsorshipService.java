package com.example.familytree.service;

import com.example.familytree.domain.FinancialSponsorship;
import com.example.familytree.exceptions.FamilyTreeException;
import java.util.List;

public interface FinancialSponsorshipService {
  FinancialSponsorship create(FinancialSponsorship financialSponsorship) throws FamilyTreeException;
  void update(FinancialSponsorship financialSponsorship) throws FamilyTreeException;
  List<FinancialSponsorship> getAll() throws FamilyTreeException;
  FinancialSponsorship getById(Long id) throws FamilyTreeException;
}
