package com.example.familytree.repository;

import com.example.familytree.domain.GuestManagement;
import com.example.familytree.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GuestManagementRepository extends JpaRepository<GuestManagement, Long>,
    JpaSpecificationExecutor<GuestManagement> {

  List<GuestManagement> findAllByEventManagementId(Long eventManagementId);
}
