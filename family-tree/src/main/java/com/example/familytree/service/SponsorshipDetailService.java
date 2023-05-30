package com.example.familytree.service;

import com.example.familytree.domain.SponsorsipDetail;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.SponsorshipDetailDTO;
import java.util.List;

public interface SponsorshipDetailService {
  SponsorsipDetail create(SponsorsipDetail sponsorsipDetail) throws FamilyTreeException;
  void update(SponsorsipDetail sponsorsipDetail) throws FamilyTreeException;
  void delete(Long id) throws FamilyTreeException;
  SponsorshipDetailDTO getAll(Long financialSponsorshipId) throws FamilyTreeException;
  SponsorsipDetail getById(Long id) throws FamilyTreeException;
}
