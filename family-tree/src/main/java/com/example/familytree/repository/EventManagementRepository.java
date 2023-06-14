package com.example.familytree.repository;

import com.example.familytree.domain.EventManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EventManagementRepository extends JpaRepository<EventManagement, Long>,
    JpaSpecificationExecutor<EventManagement> {}
