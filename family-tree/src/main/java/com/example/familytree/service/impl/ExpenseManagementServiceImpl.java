package com.example.familytree.service.impl;

import com.example.familytree.repository.ExpenseManagementRepository;
import com.example.familytree.service.ExpenseManagementService;
import com.example.familytree.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseManagementServiceImpl implements ExpenseManagementService {
  private final ExpenseManagementRepository expenseManagementRepository;
  private final MemberService memberService;
}
