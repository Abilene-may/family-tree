package com.example.familytree.repository;

import com.example.familytree.domain.RevenueManagement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RevenueManagementRepository extends JpaRepository<RevenueManagement, Long>,
    JpaSpecificationExecutor<RevenueManagement> {

  List<RevenueManagement> findAllByYear(Integer year);
}
