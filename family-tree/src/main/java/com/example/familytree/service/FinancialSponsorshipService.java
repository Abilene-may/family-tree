package com.example.familytree.service;

import com.example.familytree.domain.FinancialSponsorship;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.FinancialSponsorshipReport;
import java.time.LocalDate;
import java.util.List;

public interface FinancialSponsorshipService {
  FinancialSponsorship create(FinancialSponsorship financialSponsorship) throws FamilyTreeException;
  void update(FinancialSponsorship financialSponsorship) throws FamilyTreeException;
  List<FinancialSponsorship> getAll() throws FamilyTreeException;
  FinancialSponsorship getById(Long id) throws FamilyTreeException;

  FinancialSponsorshipReport report(LocalDate effectiveStartDate, LocalDate effectiveEndDate)
      throws FamilyTreeException;
  void delete(Long id) throws FamilyTreeException;
}
