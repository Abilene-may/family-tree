package com.example.familytree.service;

import com.example.familytree.domain.EventManagement;
import com.example.familytree.exceptions.FamilyTreeException;
import java.util.List;

public interface EventManagementService {
  EventManagement create(EventManagement eventManagement) throws FamilyTreeException;
  void update(EventManagement eventManagement) throws FamilyTreeException;
  void delete(Long id) throws FamilyTreeException;
  List<EventManagement> getAll() throws FamilyTreeException;
  EventManagement getById(Long id) throws FamilyTreeException;
}
