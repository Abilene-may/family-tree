package com.example.familytree.repository;

import com.example.familytree.domain.PermissionManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionManagementRepository extends JpaRepository<PermissionManagement, Long>,
    JpaSpecificationExecutor<PermissionManagement> {}