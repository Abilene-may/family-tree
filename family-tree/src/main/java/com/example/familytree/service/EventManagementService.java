package com.example.familytree.service;

import com.example.familytree.domain.EventManagement;
import com.example.familytree.exceptions.FamilyTreeException;
import com.example.familytree.models.eventmanagement.EventReportDTO;
import java.time.LocalDate;
import java.util.List;

public interface EventManagementService {
  EventManagement create(EventManagement eventManagement) throws FamilyTreeException;
  void update(EventManagement eventManagement) throws FamilyTreeException;
  void delete(Long id) throws FamilyTreeException;
  List<EventManagement> getAll() throws FamilyTreeException;
  EventManagement getById(Long id) throws FamilyTreeException;

  EventReportDTO report(LocalDate effectiveStartDate, LocalDate effectiveEndDate)
      throws FamilyTreeException;
}
