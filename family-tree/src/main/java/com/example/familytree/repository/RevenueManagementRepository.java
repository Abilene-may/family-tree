package com.example.familytree.repository;

import com.example.familytree.domain.RevenueManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RevenueManagementRepository extends JpaRepository<RevenueManagement, Long>,
    JpaSpecificationExecutor<RevenueManagement> {

}
